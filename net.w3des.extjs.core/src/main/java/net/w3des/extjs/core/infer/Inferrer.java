package net.w3des.extjs.core.infer;

import net.w3des.extjs.core.ExtInferenceProvider;

import org.eclipse.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.wst.jsdt.core.ast.IAllocationExpression;
import org.eclipse.wst.jsdt.core.ast.IAssignment;
import org.eclipse.wst.jsdt.core.ast.IConstructorDeclaration;
import org.eclipse.wst.jsdt.core.ast.IExpression;
import org.eclipse.wst.jsdt.core.ast.IFunctionCall;
import org.eclipse.wst.jsdt.core.ast.ILocalDeclaration;
import org.eclipse.wst.jsdt.core.ast.IObjectLiteralField;
import org.eclipse.wst.jsdt.core.ast.ITypeDeclaration;
import org.eclipse.wst.jsdt.core.dom.Message;
import org.eclipse.wst.jsdt.core.infer.InferOptions;
import org.eclipse.wst.jsdt.core.infer.InferredAttribute;
import org.eclipse.wst.jsdt.core.infer.InferredMethod;
import org.eclipse.wst.jsdt.core.infer.InferredType;
import org.eclipse.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.NullLiteral;
import org.eclipse.wst.jsdt.internal.compiler.ast.OR_OR_Expression;
import org.eclipse.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.eclipse.wst.jsdt.internal.compiler.ast.ObjectLiteralField;
import org.eclipse.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.wst.jsdt.internal.compiler.ast.StringLiteral;

@SuppressWarnings("restriction")
public class Inferrer extends ASTVisitor {

	final private CompilationUnitDeclaration unit;
	@SuppressWarnings("unused")
	final private InferOptions options;
	
	private InferredType newType;
	
	public Inferrer(CompilationUnitDeclaration parsedUnit, InferOptions inferOptions) {
		options = inferOptions;
		unit = parsedUnit;
	}
	
	@Override
	public boolean visit(IFunctionCall functionCall) {
		MessageSend mess = (MessageSend) functionCall;
		if (mess.getReceiver() == null || mess.getSelector() == null || !mess.getReceiver().toString().equals("Ext")) {
			return true;
		}
		String funcName = String.valueOf(mess.getSelector());
		if (funcName.equals("define") ) {
			define(mess);
			
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
	
	
	/**
	 * @see http://docs.sencha.com/ext-js/4-1/#!/api/Ext-method-create
	 * @param mess
	 * TODO Implement this
	 */
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

	/**
	 * @see http://docs.sencha.com/ext-js/4-1/#!/api/Ext-method-define
	 * @param mess
	 */
	private void define(MessageSend mess) {
		if (mess.getArguments().length < 1) { //invalid call
			return;
		}
			
		if ((mess.getArguments()[0] instanceof StringLiteral)) {
			StringLiteral name = (StringLiteral) mess.getArguments()[0];
			newType = unit.addType(name.source(), true, ExtInferenceProvider.ID); 
			newType.setNameStart(mess.getArguments()[0].sourceStart()+1);
			newType.isAnonymous = false;
		} else if(mess.getArguments()[0] instanceof NullLiteral) { 
			newType = unit.addType(InferredType.FUNCTION_NAME, true, ExtInferenceProvider.ID);
			newType.isAnonymous = true;
		    newType.setNameStart(mess.getArguments()[1].sourceStart());
		    newType.updatePositions(mess.getArguments()[1].sourceStart(), mess.getArguments()[1].sourceEnd());
		} else {
			return;
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
			newType.addConstructorMethod(newType.getName(), 
					((FunctionExpression) constr.receiver).getMethodDeclaration(), 
					constr.sourceStart);
		}
		
		
	}
	
	/**
	 * @param mess
	 */
	private void apply(MessageSend mess) {
		//mess.resolvedType = mess.arguments[0].resolvedType;
		mess.traverse(new ApplyInfer(newType, unit));
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
			//ExtJSCore.warn("Invalid import: " + req.toString());
		}
	}
	
	private void build(MessageSend mess) {
		
		ObjectLiteral lit = (ObjectLiteral) mess.getArguments()[1];
		String typeParent = "Ext.Base";
		boolean singleton = false;
		boolean hasConstructor = false;
		if (lit.fields != null) {
			for (ObjectLiteralField field : lit.fields) {
				if (field == null) {
					continue;
				}
				if (field.getFieldName().toString().equals("extend") || field.getFieldName().toString().equals("override")) {
					typeParent = String.valueOf(((StringLiteral) field.getInitializer()).source());
					require(field.initializer);
					continue;
				}
				
				if (field.getFieldName().toString().equals("singleton")) { 
					singleton = true;
					continue;
				}
				
				if (field.getFieldName().toString().equals("requires") || field.getFieldName().toString().equals("uses")) {
					require(field.initializer);
					continue;
				}
				
	
				if (field.initializer instanceof FunctionExpression) {
					String name = String.valueOf(field.getFieldName().toString());
					InferredMethod method;
					if (name.equals("constructor")) {
						method = newType.addConstructorMethod("constructor".toCharArray(), ((FunctionExpression) field.initializer).methodDeclaration, field.getFieldName().sourceStart());
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
				
				if (field.getFieldName().toString().equals("inheritableStatics") && field.initializer instanceof ObjectLiteral) {
					addStatics((ObjectLiteral) field.initializer);
					
					continue;
				}
				
				if (field.getFieldName().toString().equals("alternateClassName")) {
					aliases(field.getInitializer());
					
					continue;
				}
				
				if (field.getFieldName().toString().equals("mixins")) {
					mixins(field.getInitializer(), field);
					continue;
				}
				
				// TODO other special EXT elements like configs etc...
				addAttribute(field);
			}
		}
		
		if (singleton) { 
			isSingleton();
		} else if(!singleton && !hasConstructor) {
			newType.addConstructorMethod(newType.getName(), new MethodDeclaration(null), newType.getNameStart());
		}
		//TODO do it better
		if(!newType.getName().toString().equals("Ext.Base") && !newType.getName().toString().equals("Base")) {
			newType.superClass = unit.findInferredType(typeParent.toCharArray());
			if (newType.superClass == null) {
				newType.superClass = new InferredType(typeParent.toCharArray()); //TODO find another method
				newType.superClass.bits = ASTNode.TYPE_REFERENCE;
				//newType.referenceClass = newType.superClass;
				//unit.problemReporter.handle(IProblem.ImportRelated, new String[]{}, new String[] { "Unable to find : " + typeParent }, ProblemSeverities.Warning, superStart, superEnd, unit, unit.compilationResult);
			}
		}
		
	}
	// TODO parse JSDoc while reading declaration (TypeBinding)  - currently impossible in inferrer engine
	private void addAttribute(ObjectLiteralField field) {
		InferredAttribute attr = newType.addAttribute(field.getFieldName().toString().toCharArray(), field.initializer, field.getFieldName().sourceStart());
		attr.sourceStart = field.sourceStart;
		attr.sourceEnd = field.sourceEnd;
		attr.initializationStart = field.initializer.sourceStart;
		
		/*if (field.getInitializer()) {
			
		}*/
		attr.getClass();
	}
	
	/**
	 * Create mixins
	 * 
	 * @see http://docs.sencha.com/ext-js/4-1/#!/api/Ext.Class-cfg-mixins
	 * @param initializer
	 */
	private void mixins(final IExpression mixin, final ObjectLiteralField field) {
		if (mixin instanceof StringLiteral) {
			newType.addMixin(((StringLiteral) mixin).source());
		} else if (mixin instanceof ArrayInitializer) {
			for (Expression e : ((ArrayInitializer) mixin).expressions) {
				if (e != null && e instanceof StringLiteral) {
					newType.addMixin(((StringLiteral) mixin).source());
				}
			}
		} else if (mixin instanceof ObjectLiteral) {
			InferredAttribute attr = newType.findAttribute("mixins".toCharArray());
			//TODO type binding
			if (attr == null) {
				attr = newType.addAttribute("mixins".toCharArray(), field.initializer, field.getFieldName().sourceStart());
			}
			for (IObjectLiteralField f : ((ObjectLiteral) mixin).getFields()) {
				if (f.getInitializer() instanceof StringLiteral) {
					newType.addMixin(((StringLiteral) f.getInitializer()).source());
				}
			}
		}
	}

	/**
	 * @see http://docs.sencha.com/ext-js/4-1/#!/api/Ext.Class-cfg-alternateClassName
	 * @param alias
	 */
	private void aliases(IExpression alias) {
		if (alias instanceof StringLiteral) {
			aliases((StringLiteral) alias);
		} else if (alias instanceof ObjectLiteral) {
			aliases((ObjectLiteral) alias);
		} else if (alias instanceof ArrayInitializer) {
			aliases((ArrayInitializer) alias);
		} else {
			//ExtJSCore.warn("Invalid alias: " + alias.toString());
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
			InferredType alias = unit.findInferredType("Base".toCharArray());
			
			InferredType type = unit.addType("Ext.Base".toCharArray(), true, ExtInferenceProvider.ID);
			type.sourceStart = alias.sourceStart;
			type.sourceEnd = alias.sourceEnd;
			type.setNameStart(alias.getNameStart());
			type.addMixin("Base".toCharArray());
		}
		
		if (assignment.toString().endsWith("Ext = Ext || {}")) {
			assignment.setInferredType(unit.findInferredType("Ext".toCharArray()));
		}

		return super.visit(assignment); 
	}
	
	@Override
	public boolean visit(InferredAttribute inferredField) {
		return super.visit(inferredField);
	}
	
	@Override
	public boolean visit(ILocalDeclaration localDeclaration) {
		
		if (localDeclaration.getInitialization() != null && localDeclaration.getInitialization() instanceof OR_OR_Expression) {
			final OR_OR_Expression ex = (OR_OR_Expression) localDeclaration.getInitialization();
			if (ex.getLeft() instanceof SingleNameReference) {
				final SingleNameReference ref = (SingleNameReference) ex.getLeft();
				if (unit.findInferredType(ref.token) != null) {
					localDeclaration.setInferredType(unit.findInferredType(ref.token));
					
					return true;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean visit(IAllocationExpression allocationExpression) {
		// TODO Auto-generated method stub
		return super.visit(allocationExpression);
	}
	
	@Override
	public boolean visit(ITypeDeclaration localTypeDeclaration) {
		// TODO Auto-generated method stub
		return super.visit(localTypeDeclaration);
	}
	
	@Override
	public boolean visit(IConstructorDeclaration constructorDeclaration) {
		// TODO Auto-generated method stub
		return super.visit(constructorDeclaration);
	}

}
