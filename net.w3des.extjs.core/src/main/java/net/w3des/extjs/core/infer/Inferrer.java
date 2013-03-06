package net.w3des.extjs.core.infer;

import java.awt.List;

import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

import net.w3des.extjs.core.ExtInferenceProvider;
import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.wst.jsdt.core.ast.IAllocationExpression;
import org.eclipse.wst.jsdt.core.ast.IAssignment;
import org.eclipse.wst.jsdt.core.ast.ICompoundAssignment;
import org.eclipse.wst.jsdt.core.ast.IExpression;
import org.eclipse.wst.jsdt.core.ast.IFunctionCall;
import org.eclipse.wst.jsdt.core.ast.IFunctionExpression;
import org.eclipse.wst.jsdt.core.compiler.IProblem;
import org.eclipse.wst.jsdt.core.infer.InferOptions;
import org.eclipse.wst.jsdt.core.infer.InferredAttribute;
import org.eclipse.wst.jsdt.core.infer.InferredMethod;
import org.eclipse.wst.jsdt.core.infer.InferredType;
import org.eclipse.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.wst.jsdt.internal.compiler.ast.Assignment;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.NameReference;
import org.eclipse.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.eclipse.wst.jsdt.internal.compiler.ast.ObjectLiteralField;
import org.eclipse.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.wst.jsdt.internal.compiler.ast.StringLiteral;
import org.eclipse.wst.jsdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.flow.FlowContext;
import org.eclipse.wst.jsdt.internal.compiler.flow.FlowInfo;
import org.eclipse.wst.jsdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.BlockScope;
import org.eclipse.wst.jsdt.internal.compiler.lookup.ClassScope;
import org.eclipse.wst.jsdt.internal.compiler.lookup.CombinedSourceTypeBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.CompilationUnitBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.wst.jsdt.internal.compiler.lookup.NestedTypeBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.wst.jsdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.wst.jsdt.internal.compiler.problem.ProblemSeverities;

@SuppressWarnings("restriction")
public class Inferrer extends ASTVisitor {

	final private CompilationUnitDeclaration unit;
	final private InferOptions options;
	private InferredType newType;
	
	public Inferrer(CompilationUnitDeclaration parsedUnit, InferOptions inferOptions) {
		options = inferOptions;
		unit = parsedUnit;
	}
	
	@SuppressWarnings("restriction")
	@Override
	public boolean visit(IFunctionCall functionCall) {
		MessageSend mess = (MessageSend) functionCall;
		if (mess.getReceiver() == null || mess.getSelector() == null || !mess.getReceiver().toString().equals("Ext")) {
			return true;
		}
		String funcName = String.valueOf(mess.getSelector());
		if (funcName.equals("define") ) {
			createClass(mess);
			
			return true;
		} else if (funcName.equals("apply")) {
			apply(mess);
		} else if (funcName.equals("require")) {
			require(mess);
		} else if (funcName.equals("create")) {
			create(mess);
		}
		
		return true;
	}
	
	private void create(MessageSend mess) {
		if (mess.getArguments().length > 0 && mess.getArguments()[0] instanceof StringLiteral) {
			StringLiteral className = (StringLiteral) mess.getArguments()[0];
			InferredType tt = unit.findInferredType(String.valueOf(className.source()).toCharArray());
			if (tt == null) {
				tt = unit.addType(String.valueOf(className.source()).toCharArray(), false, ExtInferenceProvider.ID); //find another method
				tt.bits = ASTNode.TYPE_REFERENCE;
			}
			
			//unit.compilationUnitBinding = new CompilationUnitBinding(new CompilationUnitScope(unit, environment), new PackageBinding(environment), unit.compilationResult.fileName);
			
			if (unit.compilationUnitBinding == null) {
				//unit.compilationUnitBinding = new CompilationUnitBinding(unit.scope, unit.compilationResult.compilationUnit.getPackageName(), unit.compilationResult.fileName);
			}
			//mess.resolvedType = unit.compilationResult.;
			//mess.resolvedType = unit.compilationResult.;
		}
	}

	private void createClass(MessageSend mess) {
		if (mess.getArguments().length < 1) { //invalid call
			return;
		}
			
		if ((mess.getArguments()[0] instanceof StringLiteral)) {
			StringLiteral name = (StringLiteral) mess.getArguments()[0];
			newType = unit.addType(name.source(), true, ExtInferenceProvider.ID); 
			newType.setNameStart(mess.getArguments()[0].sourceStart()+1);
			newType.isAnonymous = false;
		} else {
			//TODO : create anonymous
			return;
			//newType = unit.addType(InferredType.FUNCTION_NAME, true, ExtInferenceProvider.ID);
			//newType.isAnonymous = true;
		}
		
		newType.sourceStart = mess.sourceStart();
		newType.sourceEnd = mess.sourceEnd();
		
		newType.isDefinition = true;
		newType.bits = ASTNode.TYPE_DECLARATION;
		
		if (mess.getArguments().length < 2 ) {
			return;
		}
		if (mess.getArguments()[1] instanceof ObjectLiteral) {
			build(mess);
		} else if (mess.getArguments()[1] instanceof MessageSend) {
			MessageSend constr = (MessageSend)mess.getArguments()[1];
			newType.addConstructorMethod("constructor".toCharArray(), 
					((FunctionExpression) constr.receiver).getMethodDeclaration(), 
					constr.sourceStart);
		}
		
		
	}
	
	/**
	 * @param mess
	 */
	private void apply(MessageSend mess) {
		//mess.resolvedType = mess.arguments[0].resolvedType;
	}
	
	private void require(MessageSend mess) {
		for(IExpression arg : mess.getArguments()) {
			if (arg != null) {
				require(arg);
			}
		}
	}
	
	private void require(StringLiteral req) {
		unit.addImport(String.valueOf(req.source()).toCharArray(), 0, 1, req.sourceStart);
	}
	
	private void require(ArrayInitializer req) {
		for(Expression ex : req.expressions) {
			if (ex != null) {
				require(ex);
			}
		}
	}
	
	private void require(IExpression req) {
		if (req instanceof ArrayInitializer) {
			require((ArrayInitializer) req);
		} else if (req instanceof StringLiteral) {
			require((StringLiteral) req);
		} else if(req instanceof SingleNameReference) {
			// ignore
			//ExtJSCore.info("Unable to track" + req.toString());
		} else {
			ExtJSCore.warn("Invalid import: " + req.toString());
		}
	}
	
	private void build(MessageSend mess) {
		
		ObjectLiteral lit = (ObjectLiteral) mess.getArguments()[1];
		String typeParent = "Ext.Base";
		boolean singleton = false;
		boolean hasConstructor = false;
		int superStart = 0;
		int superEnd = 0;
		if (lit.fields != null) {
			for (ObjectLiteralField field : lit.fields) {
				if (field == null) {
					continue;
				}
				if (field.getFieldName().toString().equals("extend") || field.getFieldName().toString().equals("override")) {
					typeParent = String.valueOf(((StringLiteral) field.getInitializer()).source());
					
					if ( unit.findInferredType(typeParent.toCharArray()) == null) {
						unit.addImport(typeParent.toCharArray(), 0, 1, field.getInitializer().sourceStart());
					}
					
					superStart = field.getInitializer().sourceStart();
					superEnd = field.getInitializer().sourceEnd();
					
					continue;
				}
				
				if (field.getFieldName().toString().equals("singleton")) { 
					singleton = true;
					continue;
				}
				
				if (field.getFieldName().toString().equals("requires")) {
					require(field.initializer);
					continue;
				}
				
	
				if (field.initializer instanceof FunctionExpression) {
					String name = String.valueOf(field.getFieldName().toString());
					InferredMethod method;
					if (name.equals("constructor")) {
						method = newType.addConstructorMethod(newType.getName(), ((FunctionExpression) field.initializer).methodDeclaration, newType.getNameStart());
						hasConstructor = true;
					} else {
						method = newType.addMethod(field.getFieldName().toString().toCharArray(), ((FunctionExpression) field.initializer).methodDeclaration, field.getFieldName().sourceStart());
					} 
					
					if (name.equals("constructor") || name.equals("initComponent")) {
						method.getFunctionDeclaration().setInferredType(newType);
					}
					
					continue;
				}
				
				if (field.getFieldName().toString().equals("statics") && field.initializer instanceof ObjectLiteral) {
					addStatics((ObjectLiteral) field.initializer);
					
					continue;
				}
				
				if (field.getFieldName().toString().equals("alternateClassName")) {
					aliases(field.getInitializer());
					
					continue;
				}
				
				// TODO other special EXT elements like configs etc...
				// TODO parse JSDoc while reading declaration (TypeBinding)
				InferredAttribute attr = newType.addAttribute(field.getFieldName().toString().toCharArray(), field.initializer, field.getFieldName().sourceStart());
				attr.getClass();
			}
		}
		
		if (singleton) { 
			isSingleton();
		} else if(!singleton && !hasConstructor) {
			newType.addConstructorMethod(newType.getName(), new MethodDeclaration(null), newType.getNameStart());
		}
		if(typeParent != "Ext.Base") { // TODO generating 
			newType.superClass = unit.findInferredType(typeParent.toCharArray());
			if (newType.superClass == null) {
				newType.superClass = new InferredType(typeParent.toCharArray()); //TODO find another method
				newType.superClass.bits = ASTNode.TYPE_REFERENCE;
				//newType.referenceClass = newType.superClass;
				//unit.problemReporter.handle(IProblem.ImportRelated, new String[]{}, new String[] { "Unable to find : " + typeParent }, ProblemSeverities.Warning, superStart, superEnd, unit, unit.compilationResult);
			}
		}
		
	}
	
	private void aliases(StringLiteral alias) {
		InferredType newAlias = unit.addType(alias.source(), true, ExtInferenceProvider.ID);
		newAlias.sourceStart = alias.sourceStart;
		newAlias.sourceEnd = alias.sourceEnd;
		newAlias.setNameStart(alias.sourceStart + 1);
		newAlias.referenceClass = newType;
	}
	
	private void aliases(ObjectLiteral alias) {
		for (ObjectLiteralField field : alias.fields) {
			if (field != null) {
				aliases(field.getInitializer());
			}
		}
	}
	
	private void aliases(ArrayInitializer alias) {
		for(Expression ex : alias.expressions) {
			if (ex != null) {
				aliases(ex);
			}
		}
	}
	
	private void aliases(IExpression alias) {
		if (alias instanceof StringLiteral) {
			aliases((StringLiteral) alias);
		} else if (alias instanceof ObjectLiteral) {
			aliases((ObjectLiteral) alias);
		} else if (alias instanceof ArrayInitializer) {
			aliases((ArrayInitializer) alias);
		} else {
			ExtJSCore.warn("Invalid alias: " + alias.toString());
		}
	}
	
	private void isSingleton() {
		if (newType.attributes != null && newType.attributes.length > 0) {
			for (InferredAttribute attr : newType.attributes) {
				if (attr != null) {
					attr.isStatic = true;
				}
			}
		}
		
		if (newType.methods != null && newType.methods.size() > 0) {
			for(Object item : newType.methods.toArray()) {
				if (item instanceof InferredMethod) {
					((InferredMethod) item).isStatic = true;
				}
			}
		}
		
		newType.bits = newType.bits | ASTNode.IsInferredType;
	}
	
	private void addStatics(ObjectLiteral initializer) {
		for (ObjectLiteralField field : initializer.fields) {
			if (field.initializer instanceof FunctionExpression) {
				InferredMethod method = newType.addMethod(field.getFieldName().toString().toCharArray(), ((FunctionExpression) field.initializer).methodDeclaration, field.getFieldName().sourceStart());
				method.isStatic = true;
			} else {
				InferredAttribute attr = newType.addAttribute(field.getFieldName().toString().toCharArray(), field.initializer, field.getFieldName().sourceStart());
				attr.isStatic = true;
			}
		}
	}
	
	@Override
	public boolean visit(InferredType inferredType) {
		return super.visit(inferredType);
	}
	
	@Override
	public boolean visit(IAssignment assignment) {
		if (assignment.toString().equals("Ext.Base = Base")) { //simple hack to register Ext.Base
			//InferredType alias = unit.findInferredType("Ext".toCharArray());
			//Assignment ass = (Assignment) assignment;
		}
		
		return super.visit(assignment); 
	}
}
