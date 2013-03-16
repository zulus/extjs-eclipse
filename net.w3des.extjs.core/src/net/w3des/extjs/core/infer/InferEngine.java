package net.w3des.extjs.core.infer;

import org.eclipse.wst.jsdt.core.ast.IAbstractVariableDeclaration;
import org.eclipse.wst.jsdt.core.ast.IArrayInitializer;
import org.eclipse.wst.jsdt.core.ast.IExpression;
import org.eclipse.wst.jsdt.core.ast.IFieldDeclaration;
import org.eclipse.wst.jsdt.core.ast.IFieldReference;
import org.eclipse.wst.jsdt.core.ast.IFunctionCall;
import org.eclipse.wst.jsdt.core.ast.IFunctionExpression;
import org.eclipse.wst.jsdt.core.ast.ILocalDeclaration;
import org.eclipse.wst.jsdt.core.ast.INullLiteral;
import org.eclipse.wst.jsdt.core.ast.IOR_OR_Expression;
import org.eclipse.wst.jsdt.core.ast.IObjectLiteral;
import org.eclipse.wst.jsdt.core.ast.IObjectLiteralField;
import org.eclipse.wst.jsdt.core.ast.ISingleNameReference;
import org.eclipse.wst.jsdt.core.ast.IStringLiteral;
import org.eclipse.wst.jsdt.core.compiler.CharOperation;
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
import org.eclipse.wst.jsdt.internal.compiler.ast.ObjectLiteral;

/**
 * TODO inferer for JSduck json
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
@SuppressWarnings("restriction")
public class InferEngine extends org.eclipse.wst.jsdt.core.infer.InferEngine {
	
	private final static char[] alias = new char[]{'a','l','i','a','s'};
	private final static char[] ext = new char[]{'E','x','t'};
	private final static char[] create = new char[]{'c','r','e','a','t','e'};
	private final static char[] define = new char[]{'d','e','f','i','n','e'};
	private final static char[] extend = new char[]{'e','x','t','e','n','d'};
	private final static char[] baseClass = new char[]{'E','x','t','.','B','a','s','e'};
	private final static char[] baseName = new char[]{'B','a','s','e'};
	private final static char[] extendedClass = new char[]{'E','x','t','.','B','a','s','e','.','A','n','o'};
	private final static char[] attrUses = new char[]{'u','s','e','s'};
	private final static char[] attrRequires = new char[]{'r','e','q','u','i','r','e','s'};
	private final static char[] attrOverride = new char[]{'o','v','e','r','r','i','d','e'};
	private final static char[] attrSingleton = new char[]{'s','i','n','g','l','e','t','o','n'};
	private final static char[] attrConstructor = new char[]{'c','o','n','s','t','r','u','c','t','o','r'};
	private final static char[] attrInitComponent = new char[]{'i','n','i','t','C','o','m','p','o','n','e','n','t'};
	private final static char[] attrStatics = new char[]{'s','t','a','t','i','c','s'};
	private final static char[] attrInheritableStatics = new char[]{'i','n','h','e','r','i','t','a','b','l','e','S','t','a','t','i','c','s'};
	private final static char[] attrAlternateClassName = new char[]{'a','l','t','e','r','n','a','t','e','C','l','a','s','s','N','a','m','e'};
	private final static char[] attrMixins = new char[]{'m','i','x','i','n','s'};
	private CompilationUnitDeclaration unit;
	
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public void doInfer() {
		super.doInfer();
		unit.traverse(new JSDuckInfer(unit));
	}

	@Override
	public void setCompilationUnit(CompilationUnitDeclaration scriptFileDeclaration) {
		super.setCompilationUnit(scriptFileDeclaration);
		unit = scriptFileDeclaration;
	}
	
	public void initializeOptions(InferOptions inferOptions) {
		//inferOptions.useAssignments = true;
		//inferOptions.useInitMethod = true;
		inferOptions.saveArgumentComments = true;
		inferOptions.engineClass = this.getClass().getName();
  	}
	
	@Override
	public boolean visit(ILocalDeclaration localDeclaration) {
		super.visit(localDeclaration);
		if (localDeclaration.getInitialization() != null && localDeclaration.getInitialization() instanceof IOR_OR_Expression) {
			final IOR_OR_Expression ex = (IOR_OR_Expression) localDeclaration.getInitialization();
			if (ex.getLeft() instanceof ISingleNameReference) {
				final ISingleNameReference ref = (ISingleNameReference) ex.getLeft();
				if (addType(ref.getToken()) != null) {
					localDeclaration.setInferredType(addType(ref.getToken()));
					
					return true;
				}
			}
		}
		
		if (localDeclaration.getInitialization() != null && localDeclaration.getInitialization() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) localDeclaration.getInitialization();
			if ((fcall.getReceiver() == null || CharOperation.prefixEquals(ext, fcall.getReceiver().toString().toCharArray(), true) ) 
					&& CharOperation.equals(fcall.getSelector(), alias)) { 
				localDeclaration.setInferredType(FunctionType); 
				// now I have to create method for left hand :( but I dont know what I shlould do if not exists
				
				return true;
			} 
			
			if (fcall.getReceiver() != null 
					&& CharOperation.equals(fcall.getReceiver().toString().toCharArray(), ext) 
					&& ( CharOperation.equals(fcall.getSelector(), extend) || CharOperation.equals(fcall.getSelector(), define) )
					&& findDefinedType(localDeclaration.getName()) == null
					&& fcall.getArguments().length > 0
					&& getArgValue(fcall.getArguments()[0]) != null ) {
				InferredType extended = addType(getArgValue(fcall.getArguments()[0]));
				InferredType type = addType(localDeclaration.getName(), true);
				type.sourceStart = localDeclaration.sourceStart();
				type.setSourceEnd(localDeclaration.sourceEnd());
				type.setNameStart(localDeclaration.sourceStart());
				type.referenceClass = extended;
				localDeclaration.setInferredType(type);
			} else if (fcall.getReceiver() != null
					&& CharOperation.equals(fcall.getReceiver().toString().toCharArray(), ext) 
					&& CharOperation.equals(fcall.getSelector(), create)
					&& findDefinedType(localDeclaration.getName()) == null
					&& fcall.getArguments().length > 0) {
				if (getArgValue(fcall.getArguments()[0]) != null) {
					localDeclaration.setInferredType(addType(getArgValue(fcall.getArguments()[0])));
				} else {
					localDeclaration.setInferredType(addType(baseClass));
				}
			}
			
		
		}
		
		return true;
	}
	
	@Override
	public InferredType findDefinedType(char[] className) {
		try {
			return super.findDefinedType(className);
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean visit(IFieldDeclaration fieldDeclaration) {
		super.visit(fieldDeclaration);
		
		if (fieldDeclaration.getInitialization() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) fieldDeclaration.getInitialization();
			if (fcall.getReceiver() != null
					&& CharOperation.equals(fcall.getReceiver().toString().toCharArray(), ext) 
					&& CharOperation.equals(fcall.getSelector(), create)
					&& findDefinedType(fieldDeclaration.getName()) == null
					&& fcall.getArguments().length > 0) {
				if (getArgValue(fcall.getArguments()[0]) != null) {
					fieldDeclaration.setInferredType(addType(getArgValue(fcall.getArguments()[0])));
				} else {
					fieldDeclaration.setInferredType(addType(baseClass));
				}
			} else if(CharOperation.equals(fcall.getSelector(), alias)) {
				fieldDeclaration.setInferredType(getByAlias(fcall.getArguments()));
			}
		}
		
		
		return true;
	}

	private InferredType buildType(IFunctionCall fcall) {
		if (fcall.getArguments().length < 1) { //invalid call
			return null;
		}
			
		InferredType newType;
		if (getArgValue(fcall.getArguments()[0]) != null) {
			char[] name = getArgValue(fcall.getArguments()[0]);
			newType = addType(name, true);
			newType.setNameStart(fcall.getArguments()[0] instanceof IStringLiteral ? fcall.getArguments()[0].sourceStart()+1 : fcall.getArguments()[0].sourceStart()); 
			newType.isAnonymous = false;
		} else if(fcall.getArguments()[0] instanceof INullLiteral) { 
			newType = createAnonymousType(extendedClass, null);
		} else {
			return null;
		}
		
		newType.sourceStart = fcall.sourceStart();
		newType.sourceEnd = fcall.sourceEnd();
		
		newType.isDefinition = true;
		newType.bits = ASTNode.TYPE_DECLARATION;
		
		if (fcall.getArguments().length < 2 ) {
			return newType;
		}
		if (fcall.getArguments()[1] instanceof ObjectLiteral) {
			buildType(newType, (ObjectLiteral) fcall.getArguments()[1]);
		} else if (fcall.getArguments()[1] instanceof MessageSend) {
			MessageSend constr = (MessageSend)fcall.getArguments()[1];
			newType.addConstructorMethod(newType.getName(), 
					((FunctionExpression) constr.receiver).getMethodDeclaration(), 
					constr.sourceStart);
		}
		
		return newType;
	}
	
	private InferredType buildType(InferredType newType, IObjectLiteral init) {
		char[] typeParent = baseClass;
		boolean singleton = false;
		boolean hasConstructor = false;
		if (init.getFields() != null) {
			for (IObjectLiteralField field : init.getFields()) {
				if (field == null) {
					continue;
				}
				char[] fieldName = field.getFieldName().toString().toCharArray();
				if (CharOperation.equals(fieldName, extend) || CharOperation.equals(fieldName, attrOverride)) {
					typeParent = getArgValue(field.getInitializer());
					continue;
				}
				
				if (CharOperation.equals(fieldName, attrSingleton)) { 
					singleton = true;
					continue;
				}
				
				if (CharOperation.equals(fieldName, attrRequires) || CharOperation.equals(fieldName, attrUses)) {
					continue;
				}
				
	
				if (getDefinedFunction(field.getInitializer()) != null) {
					InferredMethod method;
					if (CharOperation.equals(fieldName, attrConstructor)) {
						method = newType.addConstructorMethod(attrConstructor, getDefinedFunction(field.getInitializer()), field.getFieldName().sourceStart());
						hasConstructor = true;
					} else {
						method = newType.addMethod(attrConstructor, getDefinedFunction(field.getInitializer()), field.getFieldName().sourceStart());
					} 
					
					if (CharOperation.equals(fieldName, attrConstructor) || CharOperation.equals(fieldName, attrInitComponent)) {
						method.getFunctionDeclaration().setInferredType(newType);
					}
					
					continue;
				}
				
				if (CharOperation.equals(fieldName, attrStatics) || CharOperation.equals(fieldName, attrInheritableStatics)) {
					if (field.getInitializer() instanceof IObjectLiteral) {
						addStatics(newType, (IObjectLiteral) field.getInitializer());
					}
					IAbstractVariableDeclaration var = getVariable(field.getInitializer());
					if (var != null && getDefinedFunction(var.getInitialization()) instanceof IObjectLiteral) {
						addStatics(newType, (IObjectLiteral) var.getInitialization());
					}
					
					continue;
				}
				
				if (CharOperation.equals(fieldName, attrAlternateClassName)) {
					aliases(newType, field.getInitializer());
					
					continue;
				}
				
				if (CharOperation.equals(fieldName, attrMixins)) {
					mixins(newType, field);
					continue;
				}
				
				// TODO other special EXT elements like configs etc...
				addAttribute(newType, field);
			}
		}
		
		if (singleton) { 
			isSingleton(newType);
		} else if(!singleton && !hasConstructor) {
			newType.addConstructorMethod(newType.getName(), new MethodDeclaration(null), newType.getNameStart());
		}
		
		if(!CharOperation.equals(newType.getName(), baseName)) {
			newType.superClass = addType(typeParent);
		}
		
		return newType;
	}
	
	private void addAttribute(InferredType newType, IObjectLiteralField field) {
		if (field.getInitializer() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) field.getInitializer();
			if (CharOperation.equals(fcall.getSelector(), alias)) {
				MethodDeclaration methodDeclaration = new MethodDeclaration(unit.compilationResult);
				methodDeclaration.inferredType = getByAlias(fcall.getArguments());
				InferredMethod method = newType.addMethod(field.getFieldName().toString().toCharArray(), methodDeclaration, field.getFieldName().sourceStart());
				method.sourceStart = field.getInitializer().sourceStart();
				method.sourceEnd = field.getInitializer().sourceEnd();
				return;
			}
		}
		
		InferredAttribute attr = newType.addAttribute(field.getFieldName().toString().toCharArray(), field.getInitializer(), field.getFieldName().sourceStart());
		attr.sourceStart = field.sourceStart();
		attr.sourceEnd = field.sourceEnd();
		attr.initializationStart = field.getInitializer().sourceStart();
	}
	
	private void mixins(InferredType newType, final IObjectLiteralField field) {
		final IExpression mixin = field.getInitializer();
		char[] val = getArgValue(mixin);
		if (val != null) {
			newType.addMixin(val);
		} else if (mixin instanceof IArrayInitializer) {
			for (Expression e : ((ArrayInitializer) mixin).expressions) {
				if (e != null && getArgValue(e) != null) {
					newType.addMixin(getArgValue(e));
				}
			}
		} else if (mixin instanceof IObjectLiteral) {
			InferredAttribute attr = newType.findAttribute(attrMixins);
			//TODO type infereced
			if (attr == null) {
				attr = newType.addAttribute(attrMixins, field.getInitializer(), field.getFieldName().sourceStart());
			}
			for (IObjectLiteralField f : ((IObjectLiteral) mixin).getFields()) {
				if (getArgValue(f.getInitializer()) != null) {
					newType.addMixin(getArgValue(f.getInitializer()));
				}
			}
		}
	}
	
	/**
	 * @see http://docs.sencha.com/ext-js/4-1/#!/api/Ext.Class-cfg-alternateClassName
	 */
	private void aliases(InferredType newType, IExpression alias) {
		char[] name = getArgValue(alias);
		if (name != null) {
			InferredType newAlias = addType(name, true);
			newAlias.sourceStart = alias.sourceStart();
			newAlias.sourceEnd = alias.sourceEnd();
			newAlias.setNameStart(alias.sourceStart());
			newAlias.referenceClass = newType;
		} else if (alias instanceof IObjectLiteral) {
			aliases(newType, (IObjectLiteral) alias);
		} else if (alias instanceof IArrayInitializer) {
			aliases(newType, (IArrayInitializer) alias);
		}
	}
	
	private void aliases(InferredType newType, IObjectLiteral alias) {
		for (IObjectLiteralField field : alias.getFields()) {
			if (field != null && getArgValue(field.getInitializer()) != null) {
				aliases(newType, field.getInitializer());
			}
		}
	}
	
	private void aliases(InferredType newType, IArrayInitializer alias) {
		Expression[] expressions = ((ArrayInitializer) alias).expressions;
		for (int i = 0; i < expressions.length; i++) {
			Expression ex = expressions[i];
			if (ex != null && getArgValue(ex) != null) {
				aliases(newType, ex);
			}
		}
	}
	
	private void addStatics(InferredType newType, IObjectLiteral initializer) {
		for (IObjectLiteralField field : initializer.getFields()) {
			if (field.getInitializer() instanceof IFunctionExpression) {
				InferredMethod method = newType.addMethod(field.getFieldName().toString().toCharArray(), getDefinedFunction(field.getInitializer()), field.getFieldName().sourceStart());
				method.isStatic = true;
			} else {
				InferredAttribute attr = newType.addAttribute(field.getFieldName().toString().toCharArray(), field.getInitializer(), field.getFieldName().sourceStart());
				attr.isStatic = true;
			}
		}
	}
	
	private void isSingleton(InferredType newType) {
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
	}

	@Override
	public boolean visit(IFunctionCall functionCall) {
		super.visit(functionCall);
		if (CharOperation.equals(getName(functionCall.getReceiver()), ext) && 
				(CharOperation.equals(functionCall.getSelector(), define) 
						|| CharOperation.equals(functionCall.getSelector(), extend))) {
			buildType(functionCall);
			
			return true;
		} 
		
		return true;
	}
	
	
	private InferredType getByAlias(IExpression[] arguments) {
		if (arguments == null || arguments.length < 2) {
			return null;
		}
		IAbstractVariableDeclaration var = getVariable(arguments[0]);
		char[] obName = null;
		if (var == null) {
			obName = getName(arguments[0]);
		} else {
			obName = var.getName();
		}
		
		char[] fName = getName(arguments[1]);
		
		if (fName == null && !(arguments[1] instanceof IStringLiteral)) {
			var = getVariable(arguments[1]);
			if (var != null && !(var.getInitialization() instanceof IStringLiteral)) {
				return null;
			} else if (var != null) {
				fName = ((IStringLiteral) var.getInitialization()).source();
			}
			
		} else if (arguments[1] instanceof IStringLiteral) {
			fName = ((IStringLiteral) arguments[1]).source();
		}
		InferredType type = addType(CharOperation.concat(obName, fName, '.'));
		type.isAnonymous = true;
		type.referenceClass = FunctionType;
		
		return addType(CharOperation.concat(obName, fName, '.'));
	}
		
	private char[] getName(IExpression expression) {
		if (expression instanceof ISingleNameReference)
			return ((ISingleNameReference) expression).getToken();
		else if (expression instanceof IFieldReference)
			return ((IFieldReference) expression).getToken();
		return null;
	}
	
	private char[] getArgValue(IExpression ex) {
		
		if (ex instanceof IStringLiteral) {
			return ((IStringLiteral) ex).source();
		} else if (getVariable(ex) != null) {
			IAbstractVariableDeclaration var = getVariable(ex);
			if (var.getInitialization() instanceof IStringLiteral) {
				return ((IStringLiteral) var.getInitialization()).source();
			}
		}
		
		return null;
	}
	
	@Override
	public boolean visit(IObjectLiteralField field) {
		if (field.getInitializer() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) field.getInitializer();
			if (CharOperation.equals(fcall.getSelector(), alias)) {
				MethodDeclaration methodDeclaration = new MethodDeclaration(unit.compilationResult);
				methodDeclaration.inferredType = getByAlias(fcall.getArguments());
			}
		}
		
		return true;
	}
}