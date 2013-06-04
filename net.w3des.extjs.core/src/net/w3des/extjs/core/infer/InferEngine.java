package net.w3des.extjs.core.infer;

import java.util.Arrays;

import net.w3des.extjs.core.ExtJSCore;
import net.w3des.extjs.core.model.extjs.ExtJsFactory;
import net.w3des.extjs.core.model.extjs.ExtJsPackage;
import net.w3des.extjs.core.model.extjs.Widget;
import net.w3des.extjs.core.model.extjs.impl.ExtJsFactoryImpl;
import net.w3des.extjs.core.model.extjs.impl.ExtJsPackageImpl;

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
import org.eclipse.wst.jsdt.internal.compiler.ast.ThisReference;
import org.eclipse.wst.jsdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.wst.jsdt.internal.compiler.lookup.TypeConstants;

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
	private final static char[] apply = new char[] { 'a', 'p', 'p', 'l', 'y' };
	private final static char[] applyIf = new char[] { 'a', 'p', 'p', 'l', 'y', 'I', 'f' };
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
	private final static char[] attrConstructor = new char[] { 'c', 'o', 'n', 's', 't', 'r', 'u', 'c', 't', 'o', 'r' };
	private CompilationUnitDeclaration unit;
	private final static MethodDeclaration emptyDeclaration = new MethodDeclaration(null);

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
		inferOptions.useAssignments = true;
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
		if (localDeclaration.getInitialization() != null
				&& localDeclaration.getInitialization() instanceof IOR_OR_Expression) {
			final IOR_OR_Expression ex = (IOR_OR_Expression) localDeclaration.getInitialization();
			if (ex.getLeft() instanceof ISingleNameReference) {
				final ISingleNameReference ref = (ISingleNameReference) ex.getLeft();
				if (addType(ref.getToken()) != null) {
					localDeclaration.setInferredType(addType(ref.getToken()));
					return super.visit(localDeclaration);
				}
			}
		}

		if (localDeclaration.getInitialization() != null
				&& localDeclaration.getInitialization() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) localDeclaration.getInitialization();
			if (fcall.getReceiver() == null || fcall.getReceiver().toString() == null) {
				return super.visit(localDeclaration);
			}

			if (localDeclaration.getInferredType() == null) {
				localDeclaration.setInferredType(extCreate(fcall));
				return super.visit(localDeclaration);
			}

		}

		return super.visit(localDeclaration);
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

					return super.handleFunctionCall(messageSend, assignmentExpression);
				}
			} else if (isExtApply(messageSend)) {
				InferredType t = extApply(messageSend);
				if (assignmentExpression != null && t != null) {
					assignmentExpression.setInferredType(t);
				}
			}
		}

		if (messageSend.getReceiver() == null || messageSend.getReceiver().toString() == null
				|| messageSend.getSelector() == null) {
			return super.handleFunctionCall(messageSend, assignmentExpression);
		}

		if (isExtDefine(messageSend)) {
			InferredType type = buildType(messageSend);
			if (assignmentExpression != null) {
				// assignmentExpression.setInferredType(type);
			}
		} else if (assignmentExpression != null && extCreate(messageSend) != null) {
			assignmentExpression.setInferredType(extCreate(messageSend));
		} else if (assignmentExpression != null && extAlias(messageSend) != null) {
			assignmentExpression.setInferredType(extAlias(messageSend));
		}

		return super.handleFunctionCall(messageSend, assignmentExpression);
	}

	private boolean isExtApply(IFunctionCall messageSend) {
		if (messageSend.getReceiver() != null
				&& CharOperation.equals(getFieldName(messageSend.getReceiver()), ext)
				&& (CharOperation.equals(messageSend.getSelector(), apply) || CharOperation.equals(
						messageSend.getSelector(), applyIf))) {
			return true;
		}

		return false;
	}

	/**
	 * Copy from left to right
	 * 
	 * @param args
	 */
	private InferredType extApply(IFunctionCall messageSend) {
		IExpression[] args = messageSend.getArguments();
		if (args == null || args.length < 2 || passNumber < 2) {
			return null;
		}

		InferredType left = null;
		InferredType right = null;
		IAbstractVariableDeclaration vl = getVariable(args[0]);
		IAbstractVariableDeclaration vr = getVariable(args[1]);
		if (args[0] instanceof IObjectLiteral) {
			left = ((IObjectLiteral) args[0]).getInferredType();
		} else if (vl != null) {
			left = vl.getInferredType();
		} else {
			left = getTypeOf(args[0]);
		}

		if (left == null || left == FunctionType || left.superClass == FunctionType) {
			if (args[0] instanceof ISingleNameReference) {
				left = addType(getFullName(args[0]));
			} else if (args[0] instanceof IFieldReference) {
				left = addType(getFullName(args[0]));
			}
		}

		if (args[1] instanceof IObjectLiteral) {
			right = ((IObjectLiteral) args[1]).getInferredType();
		} else if (vr != null) {
			right = vr.getInferredType();
		} else {
			right = getTypeOf(args[1]);
		}

		if (right == null && args[1] instanceof ISingleNameReference) {
			right = this.findDefinedType(getFieldName(args[1]));
		} else if (right == null && args[1] instanceof IFieldReference) {
			IFieldReference ref = (IFieldReference) args[1];
			right = this.findDefinedType(ref.toString().toCharArray());
		}

		if (left == null || right == null) {
			if (left != null) {
				return left;
			}
			return null;
		}

		if (right.methods != null) {
			for (Object m : right.methods) {
				if (m == null) {
					continue;
				}
				InferredMethod method = (InferredMethod) m;
				if (left.findMethod(method.name, method.getFunctionDeclaration()) == null) {
					left.addMethod(method.name, method.getFunctionDeclaration(), method.nameStart);
				}
			}
		}

		if (right.attributes != null) {
			for (InferredAttribute attr : right.attributes) {
				if (attr == null) {
					continue;
				}

				left.addAttribute(attr);
			}
		}

		return left;
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
		if (fieldDeclaration.getInitialization() instanceof IFunctionCall) {
			IFunctionCall fcall = (IFunctionCall) fieldDeclaration.getInitialization();
			if (isExtDefine(fcall)) {
				fieldDeclaration.setInferredType(buildType(fcall));

			}
			if (extCreate(fcall) != null) {
				fieldDeclaration.setInferredType(extCreate(fcall));
			}
		}

		return super.visit(fieldDeclaration);
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
		if (args == null) {
			return null;
		}
		if (!CharOperation.equals(getName(messageSend.getReceiver()), ext)) {
			char[] className = getName(messageSend.getReceiver());
			if (className == null || className.length == 0) {
				return null;
			}
			newType = findDefinedType(className);
			if (passNumber == 2 && newType != null) {
				return newType;
			}

			newType = addType(className, true);
			if (newType.getNameStart() > 0) {
				newType.setNameStart(messageSend.sourceStart());
				newType.isAnonymous = false;
			}
		} else if (CharOperation.equals(messageSend.getSelector(), attrOverride)) {
			char[] name = getFieldName(args[0]);

			newType = findDefinedType(name);
			if (passNumber == 2 && newType != null) {
				return newType;
			}

			newType = addType(name, true);
			newType.setNameStart(args[0] instanceof IStringLiteral ? args[0].sourceStart() + 1 : args[0].sourceStart());
			newType.isAnonymous = false;
			newType.inferenceStyle = "override";
			args = Arrays.copyOfRange(args, 1, args.length);

		} else if (getArgValue(args[0]) != null) {
			char[] name = getArgValue(args[0]);

			newType = findDefinedType(name);
			if (passNumber == 2 && newType != null) {
				return newType;
			} else if (name.length == 0) {
				return null;
			}

			newType = addType(name, true);
			int nameStart = 0;
			if (args[0] instanceof IStringLiteral) {
				nameStart = args[0].sourceStart() + 1;
			} else {
				nameStart = args[0].sourceStart();
			}

			newType.setNameStart(nameStart);
			newType.isAnonymous = false;
			args = Arrays.copyOfRange(args, 1, args.length);

			newType = checkOverride(newType, args);

		} else if (args[0] instanceof INullLiteral) {
			newType = createAnonymousType(extendedClass, null);
			args = Arrays.copyOfRange(args, 1, args.length);

			newType = checkOverride(newType, args);
		} else {
			return null;
		}
		this.inferredGlobal = newType;
		if (passNumber == 2) {
			return newType;
		}
		newType.sourceStart = messageSend.sourceStart();
		newType.sourceEnd = messageSend.sourceEnd();

		newType.isDefinition = true;
		newType.bits = ASTNode.TYPE_DECLARATION;
		if (newType.inferenceStyle == null || !newType.inferenceStyle.equals("override")) {
			newType.superClass = addType(baseClass);
		}
		
		if (newType.userData == null || !(newType.userData instanceof TypeData)) {
			newType.userData = new TypeData();
		}

		if (args.length < 1) {
			return newType;
		}
		if (args[0] instanceof IObjectLiteral) {
			buildType(newType, (IObjectLiteral) args[0]);
		} else if (args[0] instanceof IFunctionExpression) {
			if (newType.findMethod(newType.getName(), getDefinedFunction(args[0])) == null) {
				newType.addConstructorMethod(newType.getName(), getDefinedFunction(args[0]), newType.getNameStart());
			}
		}

		return newType;
	}

	private InferredType checkOverride(InferredType type, IExpression[] args) {
		if (args.length >= 1 && args[0] instanceof IObjectLiteral) {
			IObjectLiteral li = (IObjectLiteral) args[0];
			if (li.getFields() != null) {
				char[] found = null;
				int nameStart = 0;
				for (IObjectLiteralField field : li.getFields()) {
					if (field == null) {
						continue;
					}

					if (CharOperation.equals(getFieldName(field.getFieldName()), attrOverride, true)) {
						found = getArgValue(field.getInitializer());
						nameStart = field.getFieldName().sourceStart();
					}
				}

				if (found != null) {
					InferredType newType = addType(found, true);
					newType.isAnonymous = false;
					newType.setNameStart(nameStart);
					newType.addMixin(type.getName());
					type.inferenceStyle = "override";
					
					return type;
				}
			}
		}

		return type;
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

	private char[] getFullName(IExpression expression) {
		if (expression == null) {
			return null;
		} else if (expression instanceof ISingleNameReference) {
			return getFieldName(expression);
		} else if (expression instanceof IFieldReference) {
			char[] str = getFullName(((IFieldReference) expression).getReceiver());
			if (str == null) {
				return getFieldName(expression);
			}

			return CharOperation.concatWith(new char[][] { str, getFieldName(expression) }, '.');
		}

		return null;
	}

	private InferredType buildType(InferredType newType, IObjectLiteral init) {
		char[] typeParent = newType.superClass == null ? null : baseClass;
		
		boolean singleton = false;
		boolean hasConstructor = false;
		init.setInferredType(newType);
		if (init.getFields() != null) {
			for (IObjectLiteralField field : init.getFields()) {
				if (field == null) {
					continue;
				}
				char[] fieldName = getFieldName(field.getFieldName());
				if (CharOperation.equals(fieldName, extend)) {
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
					if (CharOperation.equals(fieldName, attrConstructor)) {
						hasConstructor = true;
					}

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
					addAttribute(newType, field, false);
					alternateClassName(newType, field.getInitializer());
					continue;
				}

				if (CharOperation.equals(fieldName, alias)) {
					addAttribute(newType, field, false);
					aliases(newType, field.getInitializer());
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
		if (!singleton && !hasConstructor && newType.findMethod(newType.getName(), emptyDeclaration) == null) {
			newType.addConstructorMethod(newType.getName(), emptyDeclaration, newType.getNameStart());
		}
		if (typeParent != null && !CharOperation.equals(newType.getName(), baseName)) {
			newType.superClass = addType(typeParent);
		}

		return newType;
	}

	private void aliases(InferredType newType, IExpression initializer) {
		char[] val = getArgValue(initializer);

		if (val != null) {
			TypeData data = (TypeData) newType.userData;
			data.addAlias(newType.getName(), val, initializer);
		}
	}

	private InferredMethod addMethod(InferredType newType, IObjectLiteralField field, boolean isStatic) {
		char[] fieldName = getFieldName(field.getFieldName());
		boolean isConstructor = false;

		if (CharOperation.equals(fieldName, attrConstructor)) {
			fieldName = newType.getName();
			isConstructor = true;
		}

		InferredMethod method = newType.findMethod(fieldName, getDefinedFunction(field.getInitializer()));
		if (method != null) {
			return method;
		}
		final int nameStart = field.getFieldName() instanceof IStringLiteral ? field.getFieldName().sourceStart() + 1
				: field.getFieldName().sourceStart();

		if (isConstructor) {
			int tmpName = newType.getNameStart();
			method = newType.addConstructorMethod(fieldName, getDefinedFunction(field.getInitializer()), nameStart);
			newType.setNameStart(tmpName);
		} else {
			method = newType.addMethod(fieldName, getDefinedFunction(field.getInitializer()), nameStart);
		}

		if (method.getFunctionDeclaration().getInferredType() == null && !isConstructor) {
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
				.getFieldName() instanceof IStringLiteral ? field.getFieldName().sourceStart() + 1 : field
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
			ExtJSCore.info(String.valueOf(val));
			newType.addMixin(val);
		} else if (mixin instanceof IArrayInitializer) {
			for (Expression e : ((ArrayInitializer) mixin).expressions) {
				if (e != null && getArgValue(e) != null) {
					newType.addMixin(getArgValue(e));
				}
			}
		} else if (mixin instanceof IObjectLiteral) {
			IObjectLiteral lit = (IObjectLiteral) mixin;

			if (lit.getInferredType() == null) {
				InferredType t = addType(createAnonymousTypeName(lit));
				t.isObjectLiteral = true;
				t.isAnonymous = true;
				populateType(t, lit, false);
			}
			if (lit.getFields() != null) {
				for (IObjectLiteralField f : lit.getFields()) {
					if (f != null) {
						InferredAttribute at = lit.getInferredType().findAttribute(getFieldName(f.getFieldName()));
						newType.addMixin(at.name);
						if (getArgValue(f.getInitializer()) != null) {
							at.type = addType(getArgValue(f.getInitializer()));
						}
					}
				}
			}
		}
	}

	/**
	 * @see http 
	 *      ://docs.sencha.com/ext-js/4-1/#!/api/Ext.Class-cfg-alternateClassName
	 */
	private void alternateClassName(InferredType newType, IExpression alias) {
		char[] name = getArgValue(alias);
		if (name != null) {
			InferredType newAlias = addType(name, true);
			newAlias.sourceStart = alias.sourceStart();
			newAlias.sourceEnd = alias.sourceEnd();
			newAlias.setNameStart(alias.sourceStart());
			newAlias.superClass = newType;
		} else if (alias instanceof IObjectLiteral) {
			alternateClassName(newType, (IObjectLiteral) alias);
		} else if (alias instanceof IArrayInitializer) {
			alternateClassName(newType, (IArrayInitializer) alias);
		}
	}

	private void alternateClassName(InferredType newType, IObjectLiteral alias) {
		if (alias.getFields() != null) {
			for (IObjectLiteralField field : alias.getFields()) {
				if (field != null && getArgValue(field.getInitializer()) != null) {
					alternateClassName(newType, field.getInitializer());
				}
			}
		}
	}

	private void alternateClassName(InferredType newType, IArrayInitializer alias) {
		Expression[] expressions = ((ArrayInitializer) alias).expressions;
		if (expressions != null) {
			for (int i = 0; i < expressions.length; i++) {
				Expression ex = expressions[i];
				if (ex != null && getArgValue(ex) != null) {
					alternateClassName(newType, ex);
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
	protected boolean isPossibleClassName(char[] name) {
		return CharOperation.equals(name, ext);
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
		if (passNumber == 2) {
			if (literal == null || literal.getFields() == null || passNumber == 2) {
				super.endVisit(literal);

				return;
			}
			IThisReference ref = null;
			for (IObjectLiteralField field : literal.getFields()) {
				if (field.getInitializer() instanceof IThisReference && field.getFieldName().toString().equals("scope")) {
					ref = (IThisReference) field.getInitializer();
					break;
				}
			}
			super.endVisit(literal);

			if (ref == null) {
				return;
			}

			for (IObjectLiteralField field : literal.getFields()) {
				if (field.getInitializer() instanceof IFunctionExpression) {
					IFunctionExpression dec = (IFunctionExpression) field.getInitializer();
					InferredMethod method = dec.getMethodDeclaration().getInferredMethod();
					try {
						InferredType type = getTypeOf(ref);
						method.inType = type;
					} catch (Throwable e) {
						ExtJSCore.error(e);
					}

				} else if (getFunction(field.getInitializer()) != null && getTypeOf(ref) != null) {
					IAbstractFunctionDeclaration dec = getFunction(field.getInitializer());
					dec.getInferredMethod().inType = getTypeOf(ref);
				}
			}

		} else {
			super.endVisit(literal);
		}

		return;
	}

	@Override
	public void endVisit(IAssignment assignment) {
		if (assignment.getLeftHandSide() instanceof ISingleNameReference
				|| assignment.getLeftHandSide() instanceof IFieldReference) {
			char[] name = getFullName(assignment.getLeftHandSide());
			InferredType t = findDefinedType(name);
			if ((t == null || !t.isDefinition) && alternates(assignment.getExpression()) != null) {
				InferredType reference = alternates(assignment.getExpression());
				if (reference != null && t == null) {
					InferredType type = addType(name, true);
					type.superClass = reference;
					type.setNameStart(assignment.getLeftHandSide().sourceStart());
					type.sourceStart = assignment.getLeftHandSide().sourceStart();
					type.sourceEnd = assignment.getLeftHandSide().sourceEnd();
				} else {
					t.isDefinition = true;
					t.addMixin(reference.getName());
				}
			}
		}

		super.endVisit(assignment);
	}

	private InferredType alternates(IExpression expression) {
		if (expression instanceof ISingleNameReference || expression instanceof IFieldReference) {
			InferredType type = findDefinedType(getFullName(expression));
			if (type != null && type.isDefinition) {
				return type;
			}
			return null;
		} else if (expression instanceof IAssignment) {
			return alternates(((IAssignment) expression).getLeftHandSide());
		}

		return null;
	}

	@Override
	public boolean visit(IFunctionCall functionCall) {
		boolean res = super.visit(functionCall);
		if (isExtApply(functionCall)) {
			extApply(functionCall);
		}

		return res;
	}
}