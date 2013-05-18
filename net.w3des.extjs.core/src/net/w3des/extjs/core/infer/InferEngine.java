package net.w3des.extjs.core.infer;

import java.util.Arrays;

import net.w3des.extjs.core.ExtJSCore;

import org.eclipse.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.wst.jsdt.core.ast.IAbstractFunctionDeclaration;
import org.eclipse.wst.jsdt.core.ast.IAbstractVariableDeclaration;
import org.eclipse.wst.jsdt.core.ast.IArrayInitializer;
import org.eclipse.wst.jsdt.core.ast.IAssignment;
import org.eclipse.wst.jsdt.core.ast.IExpression;
import org.eclipse.wst.jsdt.core.ast.IFieldDeclaration;
import org.eclipse.wst.jsdt.core.ast.IFieldReference;
import org.eclipse.wst.jsdt.core.ast.IFunctionCall;
import org.eclipse.wst.jsdt.core.ast.IFunctionDeclaration;
import org.eclipse.wst.jsdt.core.ast.IFunctionExpression;
import org.eclipse.wst.jsdt.core.ast.IJsDoc;
import org.eclipse.wst.jsdt.core.ast.ILocalDeclaration;
import org.eclipse.wst.jsdt.core.ast.INullLiteral;
import org.eclipse.wst.jsdt.core.ast.IOR_OR_Expression;
import org.eclipse.wst.jsdt.core.ast.IObjectLiteral;
import org.eclipse.wst.jsdt.core.ast.IObjectLiteralField;
import org.eclipse.wst.jsdt.core.ast.ISingleNameReference;
import org.eclipse.wst.jsdt.core.ast.IStringLiteral;
import org.eclipse.wst.jsdt.core.ast.IThisReference;
import org.eclipse.wst.jsdt.core.compiler.CharOperation;
import org.eclipse.wst.jsdt.core.infer.InferOptions;
import org.eclipse.wst.jsdt.core.infer.InferredAttribute;
import org.eclipse.wst.jsdt.core.infer.InferredMethod;
import org.eclipse.wst.jsdt.core.infer.InferredType;
import org.eclipse.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.wst.jsdt.internal.compiler.ast.FieldReference;
import org.eclipse.wst.jsdt.internal.compiler.ast.Javadoc;
import org.eclipse.wst.jsdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.wst.jsdt.internal.compiler.classfmt.ClassFileConstants;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
@SuppressWarnings("restriction")
public class InferEngine extends org.eclipse.wst.jsdt.core.infer.InferEngine {

	private final static char[] alias = new char[] { 'a', 'l', 'i', 'a', 's' };
	private final static char[] ext = new char[] { 'E', 'x', 't' };
	private final static char[] create = new char[] { 'c', 'r', 'e', 'a', 't', 'e' };
	private final static char[] define = new char[] { 'd', 'e', 'f', 'i', 'n', 'e' };
	private final static char[] extend = new char[] { 'e', 'x', 't', 'e', 'n', 'd' };
	private final static char[] baseClass = new char[] { 'E', 'x', 't', '.', 'B', 'a', 's', 'e' };
	private final static char[] baseName = new char[] { 'B', 'a', 's', 'e' };
	private final static char[] extendedClass = new char[] { 'E', 'x', 't', '.', 'B', 'a', 's', 'e', '.', 'A', 'n', 'o' };
	private final static char[] attrUses = new char[] { 'u', 's', 'e', 's' };
	private final static char[] attrRequires = new char[] { 'r', 'e', 'q', 'u', 'i', 'r', 'e', 's' };
	private final static char[] attrOverride = new char[] { 'o', 'v', 'e', 'r', 'r', 'i', 'd', 'e' };
	private final static char[] attrSingleton = new char[] { 's', 'i', 'n', 'g', 'l', 'e', 't', 'o', 'n' };
	private final static char[] attrStatics = new char[] { 's', 't', 'a', 't', 'i', 'c', 's' };
	private final static char[] attrInheritableStatics = new char[] { 'i', 'n', 'h', 'e', 'r', 'i', 't', 'a', 'b', 'l',
			'e', 'S', 't', 'a', 't', 'i', 'c', 's' };
	private final static char[] attrAlternateClassName = new char[] { 'a', 'l', 't', 'e', 'r', 'n', 'a', 't', 'e', 'C',
			'l', 'a', 's', 's', 'N', 'a', 'm', 'e' };
	private final static char[] attrMixins = new char[] { 'm', 'i', 'x', 'i', 'n', 's' };
	private CompilationUnitDeclaration unit;

	public void initialize() {
		super.initialize();
	}

	@Override
	public void doInfer() {
		super.doInfer();
	}

	@Override
	public void setCompilationUnit(CompilationUnitDeclaration scriptFileDeclaration) {
		super.setCompilationUnit(scriptFileDeclaration);
		unit = scriptFileDeclaration;
	}

	public void initializeOptions(InferOptions inferOptions) {
		super.initializeOptions(inferOptions);
		inferOptions.useAssignments = false;
		inferOptions.useInitMethod = true;
		inferOptions.saveArgumentComments = true;
		inferOptions.engineClass = this.getClass().getName();
	}

	/**
	 * Get field name by expression
	 * 
	 * @param expression
	 * @return
	 */
	private char[] getFieldName(IExpression expression) {
		if (expression instanceof ISingleNameReference) {
			return ((ISingleNameReference) expression).getToken();
		} else if (expression instanceof IFieldReference) {
			return ((IFieldReference) expression).getToken();
		} else if (expression instanceof IStringLiteral) {
			return ((IStringLiteral) expression).source();
		}

		return new char[] {};
	}

	@Override
	public boolean visit(ILocalDeclaration localDeclaration) {
		super.visit(localDeclaration);
		if (localDeclaration.getInitialization() != null
				&& localDeclaration.getInitialization() instanceof IOR_OR_Expression) {
			final IOR_OR_Expression ex = (IOR_OR_Expression) localDeclaration.getInitialization();
			if (ex.getLeft() instanceof ISingleNameReference) {
				final ISingleNameReference ref = (ISingleNameReference) ex.getLeft();
				if (addType(ref.getToken()) != null) {
					localDeclaration.setInferredType(addType(ref.getToken()));

					return true;
				}
			}
		}

		if (localDeclaration.getInitialization() != null
				&& localDeclaration.getInitialization() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) localDeclaration.getInitialization();
			if (fcall.getReceiver() == null || fcall.getReceiver().toString() == null) {
				return true;
			}

			if (localDeclaration.getInferredType() == null) {
				localDeclaration.setInferredType(extCreate(fcall));
			}

		}

		return true;
	}

	@Override
	protected boolean handleFunctionCall(IFunctionCall messageSend, LocalDeclaration assignmentExpression) {
		if (assignmentExpression != null && assignmentExpression.getInitialization() != null
				&& assignmentExpression.getInitialization() instanceof IOR_OR_Expression) {
			final IOR_OR_Expression ex = (IOR_OR_Expression) assignmentExpression.getInitialization();
			if (ex.getLeft() instanceof ISingleNameReference) {
				final ISingleNameReference ref = (ISingleNameReference) ex.getLeft();
				if (addType(ref.getToken()) != null) {
					assignmentExpression.setInferredType(addType(ref.getToken()));

					return true;
				}
			}
		}

		if (messageSend.getReceiver() == null || messageSend.getReceiver().toString() == null
				|| messageSend.getSelector() == null) {
			return true;
		}

		if (isExtDefine(messageSend)) {
			InferredType type = buildType(messageSend);
			if (assignmentExpression != null) {
				assignmentExpression.setInferredType(type);
			}
		} else if (assignmentExpression != null && extCreate(messageSend) != null) {
			assignmentExpression.setInferredType(extCreate(messageSend));
		} else if (assignmentExpression != null && extAlias(messageSend) != null) {
			assignmentExpression.setInferredType(extAlias(messageSend));
		}

		return super.handleFunctionCall(messageSend, assignmentExpression);
	}

	protected InferredType extCreate(IFunctionCall messageSend) {
		if (messageSend.getReceiver() != null && CharOperation.equals(getFieldName(messageSend.getReceiver()), ext)
				&& CharOperation.equals(messageSend.getSelector(), create) && messageSend.getArguments() != null
				&& messageSend.getArguments().length > 0) {
			if (getArgValue(messageSend.getArguments()[0]) != null) {
				return addType(getArgValue(messageSend.getArguments()[0]));
			} else {
				return addType(baseClass);
			}
		}

		return null;
	}

	@Override
	public InferredType findDefinedType(char[] className) {
		try {
			return super.findDefinedType(className);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean visit(IFieldDeclaration fieldDeclaration) {
		super.visit(fieldDeclaration);

		if (fieldDeclaration.getInitialization() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) fieldDeclaration.getInitialization();
			if (fcall.getReceiver() != null && CharOperation.equals(getFieldName(fcall.getReceiver()), ext)
					&& CharOperation.equals(fcall.getSelector(), create)
					&& findDefinedType(fieldDeclaration.getName()) == null && fcall.getArguments().length > 0) {
				if (getArgValue(fcall.getArguments()[0]) != null) {
					fieldDeclaration.setInferredType(addType(getArgValue(fcall.getArguments()[0])));
				} else {
					fieldDeclaration.setInferredType(addType(baseClass));
				}
			}
		}

		return true;
	}

	private boolean isExtDefine(IFunctionCall messageSend) {
		if (((CharOperation.equals(getName(messageSend.getReceiver()), ext) && CharOperation.equals(
				messageSend.getSelector(), define))
				|| (CharOperation.equals(getName(messageSend.getReceiver()), ext) && CharOperation.equals(
						messageSend.getSelector(), extend)) || CharOperation.equals(messageSend.getSelector(),
				attrOverride)) && passNumber == 1) {
			return true;
		}

		return false;
	}

	private InferredType buildType(IFunctionCall messageSend) {
		if (messageSend.getArguments() == null || messageSend.getArguments().length < 1) { // invalid
			return null;
		}

		InferredType newType;
		IExpression[] args = messageSend.getArguments();
		if (!CharOperation.equals(getName(messageSend.getReceiver()), ext)) {
			char[] className = getName(messageSend.getReceiver());
			if (className == null || className.length == 0) {
				return null;
			}
			newType = addType(className, true);
			if (newType.getNameStart() > 0) {
				newType.setNameStart(messageSend.sourceStart());
				newType.isAnonymous = false;
			}
		} else if (getArgValue(messageSend.getArguments()[0]) != null) {
			char[] name = getArgValue(messageSend.getArguments()[0]);
			newType = addType(name, true);
			newType.setNameStart(messageSend.getArguments()[0] instanceof IStringLiteral ? messageSend.getArguments()[0]
					.sourceStart() + 1 : messageSend.getArguments()[0].sourceStart());
			newType.isAnonymous = false;
			args = Arrays.copyOfRange(args, 1, args.length);

		} else if (messageSend.getArguments()[0] instanceof INullLiteral) {
			newType = createAnonymousType(extendedClass, null);
			args = Arrays.copyOfRange(args, 1, args.length);
		} else {
			return null;
		}
		this.inferredGlobal = newType;
		newType.sourceStart = messageSend.sourceStart();
		newType.sourceEnd = messageSend.sourceEnd();

		newType.isDefinition = true;
		newType.bits = ASTNode.TYPE_DECLARATION;
		newType.superClass = addType(baseClass);

		if (args.length < 1) {
			return newType;
		}
		if (args[0] instanceof IObjectLiteral) {
			buildType(newType, (IObjectLiteral) args[0]);
		} else if (args[0] instanceof IFunctionExpression) {
			newType.addConstructorMethod(newType.getName(), getDefinedFunction(args[0]), newType.getNameStart());
		}

		return newType;
	}

	@Override
	protected InferredType getTypeOf(IExpression expression) {
		if (expression instanceof IFunctionCall) {
			IFunctionCall messageSend = (IFunctionCall) expression;

			if (extCreate(messageSend) != null) {
				return extCreate(messageSend);
			}

			if (extAlias(messageSend) != null) {
				return extAlias(messageSend);
			}
		}

		return super.getTypeOf(expression);
	}

	@Override
	protected boolean handleFunctionExpressionAssignment(IAssignment assignment) {
		// allow inject scope in future

		return super.handleFunctionExpressionAssignment(assignment);
	}

	private InferredType buildType(InferredType newType, IObjectLiteral init) {
		char[] typeParent = baseClass;
		boolean singleton = false;
		boolean hasConstructor = false;
		init.setInferredType(newType);
		if (init.getFields() != null) {
			for (IObjectLiteralField field : init.getFields()) {
				if (field == null) {
					continue;
				}
				char[] fieldName = getFieldName(field.getFieldName());
				if (CharOperation.equals(fieldName, extend) || CharOperation.equals(fieldName, attrOverride)) {
					typeParent = getArgValue(field.getInitializer());
					addAttribute(newType, field, false);
					continue;
				}

				if (CharOperation.equals(fieldName, attrSingleton)) {
					singleton = true;
					addAttribute(newType, field, false);
					continue;
				}

				if (CharOperation.equals(fieldName, attrRequires) || CharOperation.equals(fieldName, attrUses)) {
					addImports(newType, field);
					addAttribute(newType, field, false);
					continue;
				}

				if (getDefinedFunction(field.getInitializer()) != null) {
					addMethod(newType, field, false);

					continue;
				}

				if (CharOperation.equals(fieldName, attrStatics)
						|| CharOperation.equals(fieldName, attrInheritableStatics)) {
					if (field.getInitializer() instanceof IObjectLiteral) {
						addStatics(newType, (IObjectLiteral) field.getInitializer());
					}

					continue;
				}

				if (CharOperation.equals(fieldName, attrAlternateClassName)) {
					aliases(newType, field.getInitializer());
					addAttribute(newType, field, false);
					continue;
				}

				if (CharOperation.equals(fieldName, attrMixins)) {
					mixins(newType, field);
					continue;
				}

				// TODO other special EXT elements like configs etc...
				addAttribute(newType, field, false);
			}
		}

		if (singleton) {
			isSingleton(newType);
		}
		if (!singleton && !hasConstructor) {
			newType.addConstructorMethod(newType.getName(), new MethodDeclaration(null), newType.getNameStart());
		}
		if (typeParent != null && !CharOperation.equals(newType.getName(), baseName)) {
			newType.superClass = addType(typeParent);
		}

		return newType;
	}

	private InferredMethod addMethod(InferredType newType, IObjectLiteralField field, boolean isStatic) {
		char[] fieldName = getFieldName(field.getFieldName());
		InferredMethod method = newType.findMethod(fieldName, getDefinedFunction(field.getInitializer()));
		if (method != null) {
			return method;
		}
		method = newType.addMethod(fieldName, getDefinedFunction(field.getInitializer()),
				field.getFieldName() instanceof IStringLiteral ? field.getFieldName().sourceStart() + 1 : field
						.getFieldName().sourceStart());

		if (method.getFunctionDeclaration().getInferredType() == null) {
			method.getFunctionDeclaration().setInferredType(getReturnType(field.getJsDoc()));
		}

		method.bits = method.bits | ClassFileConstants.AccPublic;

		method.isStatic = isStatic;
		if (isStatic) {
			method.bits = method.bits | ClassFileConstants.AccStatic;
		}

		/**
		 * Simple (unsafe hack) with return statement
		 */
		MethodDeclaration declaration = (MethodDeclaration) method.getFunctionDeclaration();
		declaration.javadoc = (Javadoc) field.getJsDoc();

		return method;
	}

	/**
	 * Imports I'm not how JSDT use this
	 * 
	 * @param newType
	 * @param field
	 */
	private void addImports(final InferredType newType, IObjectLiteralField field) {
		field.getInitializer().traverse(new ASTVisitor() {
			@Override
			public boolean visit(IStringLiteral stringLiteral) {
				unit.addImport(stringLiteral.source(), stringLiteral.sourceStart(), stringLiteral.sourceEnd(),
						stringLiteral.sourceStart());
				return true;
			}
		});
	}

	/**
	 * Read jsdoc and return inferred type from "@return"
	 * 
	 * @param doc
	 * @return
	 */
	private InferredType getReturnType(IJsDoc doc) {
		if (doc == null || !(doc instanceof Javadoc)) {
			return null;
		}
		Javadoc jdoc = (Javadoc) doc;
		if (jdoc.returnType != null && jdoc.returnType.getFullTypeName() != null) {
			return addType(jdoc.returnType.getFullTypeName());
		}

		return null;
	}

	private InferredAttribute addAttribute(InferredType newType, IObjectLiteralField field, boolean isStatic) {
		if (newType.findAttribute(getFieldName(field.getFieldName())) != null) {
			return newType.findAttribute(getFieldName(field.getFieldName()));
		}

		InferredAttribute attr = newType.addAttribute(getFieldName(field.getFieldName()), field.getInitializer(), field
				.getFieldName().sourceStart());
		attr.sourceStart = field.sourceStart();
		attr.sourceEnd = field.sourceEnd();
		attr.initializationStart = field.getInitializer().sourceStart();

		if (field.getJsDoc() != null) {
			Javadoc doc = (Javadoc) field.getJsDoc();
			attr.modifiers = doc.modifiers;
			if ((ClassFileConstants.AccPublic & attr.modifiers) != ClassFileConstants.AccPublic
					&& (ClassFileConstants.AccProtected & attr.modifiers) != ClassFileConstants.AccProtected
					&& (ClassFileConstants.AccPrivate & attr.modifiers) != ClassFileConstants.AccPrivate) {
				attr.modifiers = ClassFileConstants.AccPublic;
			}
			if (getReturnType(doc) != null) {
				attr.type = getReturnType(doc);
			}
		} else {
			attr.modifiers = ClassFileConstants.AccPublic;
		}

		if (isStatic) {
			attr.isStatic = isStatic;
			attr.modifiers = attr.modifiers | ClassFileConstants.AccStatic;
		}

		if (attr.type == null) {
			attr.type = getTypeOf(field.getInitializer());
		}

		handleAttributeDeclaration(attr, field.getInitializer());

		return attr;
	}

	private void mixins(InferredType newType, final IObjectLiteralField field) {
		final IExpression mixin = field.getInitializer();
		char[] val = getArgValue(mixin);
		InferredAttribute attr = addAttribute(newType, field, false);
		attr.modifiers = ClassFileConstants.AccPrivate;
		if (val != null) {
			newType.addMixin(val);
		} else if (mixin instanceof IArrayInitializer) {
			for (Expression e : ((ArrayInitializer) mixin).expressions) {
				if (e != null && getArgValue(e) != null) {
					newType.addMixin(getArgValue(e));
				}
			}
		} else if (mixin instanceof IObjectLiteral) {
			// TODO list of mixins
			IObjectLiteral lit = (IObjectLiteral) mixin;

			if (lit.getInferredType() == null) {
				InferredType t = addType(createAnonymousTypeName(lit));
				t.isObjectLiteral = true;
				t.isAnonymous = true;
				populateType(t, lit, false);
			}
			for (IObjectLiteralField f : lit.getFields()) {
				if (f != null) {
					InferredAttribute at = lit.getInferredType().findAttribute(getFieldName(f.getFieldName()));
					newType.addMixin(at.name);
					at.type = addType(getArgValue(f.getInitializer()));
				}
			}
		}
	}

	/**
	 * @see http 
	 *      ://docs.sencha.com/ext-js/4-1/#!/api/Ext.Class-cfg-alternateClassName
	 */
	private void aliases(InferredType newType, IExpression alias) {
		char[] name = getArgValue(alias);
		if (name != null) {
			InferredType newAlias = addType(name, true);
			newAlias.sourceStart = alias.sourceStart();
			newAlias.sourceEnd = alias.sourceEnd();
			newAlias.setNameStart(alias.sourceStart());
			newAlias.superClass = newType;
		} else if (alias instanceof IObjectLiteral) {
			aliases(newType, (IObjectLiteral) alias);
		} else if (alias instanceof IArrayInitializer) {
			aliases(newType, (IArrayInitializer) alias);
		}
	}

	private void aliases(InferredType newType, IObjectLiteral alias) {
		if (alias.getFields() != null) {
			for (IObjectLiteralField field : alias.getFields()) {
				if (field != null && getArgValue(field.getInitializer()) != null) {
					aliases(newType, field.getInitializer());
				}
			}
		}
	}

	private void aliases(InferredType newType, IArrayInitializer alias) {
		Expression[] expressions = ((ArrayInitializer) alias).expressions;
		if (expressions != null) {
			for (int i = 0; i < expressions.length; i++) {
				Expression ex = expressions[i];
				if (ex != null && getArgValue(ex) != null) {
					aliases(newType, ex);
				}
			}
		}
	}

	private void addStatics(InferredType newType, IObjectLiteral initializer) {
		initializer.setInferredType(newType);

		if (initializer.getFields() == null || newType == null) {
			return;
		}

		for (IObjectLiteralField field : initializer.getFields()) {
			if (field == null) {
				continue;
			}
			if (getDefinedFunction(field.getInitializer()) != null) {
				addMethod(newType, field, true);
			} else {
				addAttribute(newType, field, true);
			}
		}
	}

	@Override
	protected void populateType(InferredType type, IObjectLiteral objLit, boolean isStatic) {
		super.populateType(type, objLit, isStatic);
	}

	@Override
	protected boolean handleAttributeDeclaration(InferredAttribute attribute, IExpression initializer) {

		if (initializer instanceof IFunctionCall) {
			IFunctionCall messageSend = (IFunctionCall) initializer;
			if (extAlias(messageSend) != null) {
				attribute.type = extAlias(messageSend);
			}
		}

		return super.handleAttributeDeclaration(attribute, initializer);
	}

	/**
	 * Quick fix due JSDT limitation
	 * 
	 * @param newType
	 */
	private void isSingleton(InferredType newType) {
		newType.inferenceStyle = "singleton";
		newType.bits = InferredType.IsInferredType;

		if (newType.attributes != null && newType.attributes.length > 0) {
			for (InferredAttribute attr : newType.attributes) {
				if (attr != null) {
					attr.isStatic = true;
					attr.modifiers = attr.modifiers | ClassFileConstants.AccStatic;
				}
			}
		}

		if (newType.methods != null && newType.methods.size() > 0) {
			for (Object item : newType.methods.toArray()) {
				if (item instanceof InferredMethod) {
					InferredMethod method = (InferredMethod) item;
					method.isStatic = true;
					method.bits = method.bits | ClassFileConstants.AccStatic;
				}
			}
		}
	}

	@Override
	public boolean visit(IFunctionCall functionCall) {
		if (isExtDefine(functionCall)) {
			buildType(functionCall);

			return true;
		}
		super.visit(functionCall);

		return true;
	}

	protected InferredType extAlias(IFunctionCall messageSend) {
		if (CharOperation.equals(messageSend.getSelector(), alias) && messageSend.getArguments() != null) {
			return getFunctionByAlias(messageSend.getArguments());
		}

		return null;
	}

	private InferredType getFunctionByAlias(IExpression[] arguments) {
		char[][] arr = readFunctionAlias(arguments);
		if (arr == null) {
			return null;
		}

		InferredType type = addType(CharOperation.concat(arr[0], arr[1], '.'));
		type.superClass = FunctionType;
		type.isObjectLiteral = true;

		return type;
	}

	private char[][] readFunctionAlias(IExpression[] arguments) {
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

		return new char[][] { obName, fName };
	}

	protected IFunctionDeclaration getDefinedFunction(IExpression expression) {
		if (expression instanceof IFunctionCall) {
			IFunctionCall messageSend = (IFunctionCall) expression;
			if (CharOperation.equals(messageSend.getSelector(), alias)
					&& (messageSend.getReceiver() == null || CharOperation.equals(getName(messageSend.getReceiver()),
							ext))) {

				char[][] arr = readFunctionAlias(messageSend.getArguments());
				if (arr != null) {
					FieldReference ref = new FieldReference(arr[1], messageSend.sourceStart());
					ref.receiver = new SingleNameReference(arr[0], messageSend.sourceStart());
					return super.getDefinedFunction(ref);
				}
			}
		}

		return super.getDefinedFunction(expression);
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
	public void endVisit(IObjectLiteral literal) {
		// TODO move scope resolver
		if (literal == null || literal.getFields() == null || passNumber == 2) {
			try {
				super.endVisit(literal);
			} catch (Throwable e) {
				ExtJSCore.error(e);
			}

			return;
		}
		IThisReference ref = null;
		for (IObjectLiteralField field : literal.getFields()) {
			if (field.getInitializer() instanceof IThisReference && field.getFieldName().toString().equals("scope")) {
				ref = (IThisReference) field.getInitializer();
				break;
			}
		}
		try {
			super.endVisit(literal);
		} catch (Throwable e) {
			ExtJSCore.error(e);
		}
		if (ref == null) {
			return;
		}

		for (IObjectLiteralField field : literal.getFields()) {
			if (field.getInitializer() instanceof IFunctionExpression) {
				IFunctionExpression dec = (IFunctionExpression) field.getInitializer();
				InferredMethod method = dec.getMethodDeclaration().getInferredMethod();
				InferredType type = getTypeOf(ref);
				method.inType = type;

				method.getClass();
			} else if (getFunction(field.getInitializer()) != null && getTypeOf(ref) != null) {
				IAbstractFunctionDeclaration dec = getFunction(field.getInitializer());
				dec.getInferredMethod().inType = getTypeOf(ref);
			}
		}

		return;
	}

}