package org.adamalang.translator.tree.expressions.operators;

import org.adamalang.translator.env.Environment;
import org.adamalang.translator.parser.token.Token;
import org.adamalang.translator.tree.common.DocumentPosition;
import org.adamalang.translator.tree.common.TokenizedItem;
import org.adamalang.translator.tree.types.TyType;
import org.adamalang.translator.tree.types.TypeBehavior;
import org.adamalang.translator.tree.types.natives.*;
import org.adamalang.translator.tree.types.reactive.*;

import java.util.HashMap;

/** This is the table of how types use operators */
public class BinaryOperatorTable {
  private final HashMap<String, BinaryOperatorResult> table;
  private BinaryOperatorTable() {
    this.table = new HashMap<>();
    TyType tyInt = new TyNativeInteger(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("int"));
    TyType tyLong = new TyNativeLong(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("long"));
    TyType tyDouble = new TyNativeDouble(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("double"));
    TyType tyBoolean = new TyNativeBoolean(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("boolean"));
    TyType tyString = new TyNativeString(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("string"));
    TyType tyComplex = new TyNativeComplex(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("complex"));
    TyType tyLabel = new TyNativeString(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("label"));
    TyType tyClient = new TyNativeClient(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("label"));
    TyType tyAsset = new TyNativeAsset(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("label"));

    TyType tyMaybeBoolean = new TyNativeMaybe(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("maybe"), new TokenizedItem<>(tyBoolean));
    TyType tyMaybeInt = new TyNativeMaybe(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("maybe"), new TokenizedItem<>(tyInt));
    TyType tyMaybeLong = new TyNativeMaybe(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("maybe"), new TokenizedItem<>(tyLong));
    TyType tyMaybeDouble = new TyNativeMaybe(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("maybe"), new TokenizedItem<>(tyDouble));
    TyType tyMaybeComplex = new TyNativeMaybe(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("maybe"), new TokenizedItem<>(tyComplex));
    TyType tyMaybeString = new TyNativeMaybe(TypeBehavior.ReadOnlyNativeValue, null, Token.WRAP("maybe"), new TokenizedItem<>(tyString));

    TyType tyRxInteger = new TyReactiveInteger(Token.WRAP("int"));
    TyType tyRxLong = new TyReactiveLong(Token.WRAP("long"));
    TyType tyRxDouble = new TyReactiveDouble(Token.WRAP("double"));
    TyType tyRxString = new TyReactiveString(Token.WRAP("string"));
    TyType tyRxComplex = new TyReactiveComplex(Token.WRAP("complex"));

    TyType tyListRxInteger = new TyNativeList(TypeBehavior.ReadWriteNative, null, null, new TokenizedItem<>(tyRxInteger));
    TyType tyListRxLong = new TyNativeList(TypeBehavior.ReadWriteNative, null, null, new TokenizedItem<>(tyRxLong));
    TyType tyListRxDouble = new TyNativeList(TypeBehavior.ReadWriteNative, null, null, new TokenizedItem<>(tyRxDouble));
    TyType tyListRxString = new TyNativeList(TypeBehavior.ReadWriteNative, null, null, new TokenizedItem<>(tyRxString));
    TyType tyListRxComplex = new TyNativeList(TypeBehavior.ReadWriteNative, null, null, new TokenizedItem<>(tyRxComplex));

    // DIVISION (I,L,D,mD,C,mC)x(I,L,D,mD,C,mC)
    {
      insert(tyInt, "/", tyInt, tyMaybeDouble, "LibArithmetic.Divide.II(%s, %s)", false);
      insert(tyInt, "/", tyLong, tyMaybeDouble, "LibArithmetic.Divide.IL(%s, %s)", false);
      insert(tyInt, "/", tyDouble, tyMaybeDouble, "LibArithmetic.Divide.ID(%s, %s)", false);
      insert(tyInt, "/", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Divide.ImD(%s, %s)", false);
      insert(tyInt, "/", tyComplex, tyMaybeComplex, "LibArithmetic.Divide.IC(%s, %s)", false);
      insert(tyInt, "/", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Divide.ImC(%s, %s)", false);
      insert(tyLong, "/", tyInt, tyMaybeDouble, "LibArithmetic.Divide.LI(%s, %s)", false);
      insert(tyLong, "/", tyLong, tyMaybeDouble, "LibArithmetic.Divide.LL(%s, %s)", false);
      insert(tyLong, "/", tyDouble, tyMaybeDouble, "LibArithmetic.Divide.LD(%s, %s)", false);
      insert(tyLong, "/", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Divide.LmD(%s, %s)", false);
      insert(tyLong, "/", tyComplex, tyMaybeComplex, "LibArithmetic.Divide.LC(%s, %s)", false);
      insert(tyLong, "/", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Divide.LmC(%s, %s)", false);
      insert(tyDouble, "/", tyInt, tyMaybeDouble, "LibArithmetic.Divide.DI(%s, %s)", false);
      insert(tyDouble, "/", tyLong, tyMaybeDouble, "LibArithmetic.Divide.DL(%s, %s)", false);
      insert(tyDouble, "/", tyDouble, tyMaybeDouble, "LibArithmetic.Divide.DD(%s, %s)", false);
      insert(tyDouble, "/", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Divide.DmD(%s, %s)", false);
      insert(tyDouble, "/", tyComplex, tyMaybeComplex, "LibArithmetic.Divide.DC(%s, %s)", false);
      insert(tyDouble, "/", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Divide.DmC(%s, %s)", false);
      insert(tyMaybeDouble, "/", tyInt, tyMaybeDouble, "LibArithmetic.Divide.mDI(%s, %s)", false);
      insert(tyMaybeDouble, "/", tyLong, tyMaybeDouble, "LibArithmetic.Divide.mDL(%s, %s)", false);
      insert(tyMaybeDouble, "/", tyDouble, tyMaybeDouble, "LibArithmetic.Divide.mDD(%s, %s)", false);
      insert(tyMaybeDouble, "/", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Divide.mDmD(%s, %s)", false);
      insert(tyMaybeDouble, "/", tyComplex, tyMaybeComplex, "LibArithmetic.Divide.mDC(%s, %s)", false);
      insert(tyMaybeDouble, "/", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Divide.mDmC(%s, %s)", false);
      insert(tyComplex, "/", tyInt, tyMaybeComplex, "LibArithmetic.Divide.CI(%s, %s)", false);
      insert(tyComplex, "/", tyLong, tyMaybeComplex, "LibArithmetic.Divide.CL(%s, %s)", false);
      insert(tyComplex, "/", tyDouble, tyMaybeComplex, "LibArithmetic.Divide.CD(%s, %s)", false);
      insert(tyComplex, "/", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Divide.CmD(%s, %s)", false);
      insert(tyComplex, "/", tyComplex, tyMaybeComplex, "LibArithmetic.Divide.CC(%s, %s)", false);
      insert(tyComplex, "/", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Divide.CmC(%s, %s)", false);
      insert(tyMaybeComplex, "/", tyInt, tyMaybeComplex, "LibArithmetic.Divide.mCI(%s, %s)", false);
      insert(tyMaybeComplex, "/", tyLong, tyMaybeComplex, "LibArithmetic.Divide.mCL(%s, %s)", false);
      insert(tyMaybeComplex, "/", tyDouble, tyMaybeComplex, "LibArithmetic.Divide.mCD(%s, %s)", false);
      insert(tyMaybeComplex, "/", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Divide.mCmD(%s, %s)", false);
      insert(tyMaybeComplex, "/", tyComplex, tyMaybeComplex, "LibArithmetic.Divide.mCC(%s, %s)", false);
      insert(tyMaybeComplex, "/", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Divide.mCmC(%s, %s)", false);
    }
    // MULTIPLICATION
    {
      insert(tyInt, "*", tyInt, tyInt, "%s * %s", false);
      insert(tyInt, "*", tyLong, tyLong, "%s * %s", false);
      insert(tyInt, "*", tyDouble, tyDouble, "%s * %s", false);
      insert(tyInt, "*", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Multiply.mDI(%s, %s)", true);
      insert(tyInt, "*", tyComplex, tyComplex, "LibArithmetic.Multiply.CI(%s, %s)", true);
      insert(tyInt, "*", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Multiply.mCI(%s, %s)", true);
      insert(tyLong, "*", tyInt, tyLong, "%s * %s", false);
      insert(tyLong, "*", tyLong, tyLong, "%s * %s", false);
      insert(tyLong, "*", tyDouble, tyDouble, "%s * %s", false);
      insert(tyLong, "*", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Multiply.mDL(%s, %s)", true);
      insert(tyLong, "*", tyComplex, tyComplex, "LibArithmetic.Multiply.CL(%s, %s)", true);
      insert(tyLong, "*", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Multiply.mCL(%s, %s)", true);
      insert(tyDouble, "*", tyInt, tyDouble, "%s * %s", false);
      insert(tyDouble, "*", tyLong, tyDouble, "%s * %s", false);
      insert(tyDouble, "*", tyDouble, tyDouble, "%s * %s", false);
      insert(tyDouble, "*", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Multiply.mDD(%s, %s)", true);
      insert(tyDouble, "*", tyComplex, tyComplex, "LibArithmetic.Multiply.CD(%s, %s)", true);
      insert(tyDouble, "*", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Multiply.mCD(%s, %s)", true);
      insert(tyMaybeDouble, "*", tyInt, tyMaybeDouble, "LibArithmetic.Multiply.mDI(%s, %s)", false);
      insert(tyMaybeDouble, "*", tyLong, tyMaybeDouble, "LibArithmetic.Multiply.mDL(%s, %s)", false);
      insert(tyMaybeDouble, "*", tyDouble, tyMaybeDouble, "LibArithmetic.Multiply.mDD(%s, %s)", false);
      insert(tyMaybeDouble, "*", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Multiply.mDmD(%s, %s)", false);
      insert(tyMaybeDouble, "*", tyComplex, tyMaybeComplex, "LibArithmetic.Multiply.CmD(%s, %s)", true);
      insert(tyMaybeDouble, "*", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Multiply.mCmD(%s, %s)", true);
      insert(tyComplex, "*", tyInt, tyComplex, "LibArithmetic.Multiply.CI(%s, %s)", false);
      insert(tyComplex, "*", tyLong, tyComplex, "LibArithmetic.Multiply.CL(%s, %s)", false);
      insert(tyComplex, "*", tyDouble, tyComplex, "LibArithmetic.Multiply.CD(%s, %s)", false);
      insert(tyComplex, "*", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Multiply.CmD(%s, %s)", false);
      insert(tyComplex, "*", tyComplex, tyComplex, "LibArithmetic.Multiply.CC(%s, %s)", false);
      insert(tyComplex, "*", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Multiply.CmC(%s, %s)", false);
      insert(tyMaybeComplex, "*", tyInt, tyMaybeComplex, "LibArithmetic.Multiply.mCI(%s, %s)", false);
      insert(tyMaybeComplex, "*", tyLong, tyMaybeComplex, "LibArithmetic.Multiply.mCL(%s, %s)", false);
      insert(tyMaybeComplex, "*", tyDouble, tyMaybeComplex, "LibArithmetic.Multiply.mCD(%s, %s)", false);
      insert(tyMaybeComplex, "*", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Multiply.mCmD(%s, %s)", false);
      insert(tyMaybeComplex, "*", tyComplex, tyMaybeComplex, "LibArithmetic.Multiply.mCC(%s, %s)", false);
      insert(tyMaybeComplex, "*", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Multiply.mCmC(%s, %s)", false);
      insert(tyString, "*", tyInt, tyString, "LibString.multiply(%s, %s)", false);
      insert(tyInt, "*", tyString, tyString, "LibString.multiply(%s, %s)", true);
      insert(tyMaybeString, "*", tyInt, tyMaybeString, "LibString.multiply(%s, %s)", false);
      insert(tyInt, "*", tyMaybeString, tyMaybeString, "LibString.multiply(%s, %s)", true);
    }
    // ADDITION
    {
      insert(tyInt, "+", tyInt, tyInt, "%s + %s", false);
      insert(tyInt, "+", tyLong, tyLong, "%s + %s", false);
      insert(tyInt, "+", tyDouble, tyDouble, "%s + %s", false);
      insert(tyInt, "+", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Add.mDI(%s, %s)", true);
      insert(tyInt, "+", tyComplex, tyComplex, "LibArithmetic.Add.CI(%s, %s)", true);
      insert(tyInt, "+", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Add.mCI(%s, %s)", true);
      insert(tyLong, "+", tyInt, tyLong, "%s + %s", false);
      insert(tyLong, "+", tyLong, tyLong, "%s + %s", false);
      insert(tyLong, "+", tyDouble, tyDouble, "%s + %s", false);
      insert(tyLong, "+", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Add.mDL(%s, %s)", true);
      insert(tyLong, "+", tyComplex, tyComplex, "LibArithmetic.Add.CL(%s, %s)", true);
      insert(tyLong, "+", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Add.mCL(%s, %s)", true);
      insert(tyDouble, "+", tyInt, tyDouble, "%s + %s", false);
      insert(tyDouble, "+", tyLong, tyDouble, "%s + %s", false);
      insert(tyDouble, "+", tyDouble, tyDouble, "%s + %s", false);
      insert(tyDouble, "+", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Add.mDD(%s, %s)", true);
      insert(tyDouble, "+", tyComplex, tyComplex, "LibArithmetic.Add.CD(%s, %s)", true);
      insert(tyDouble, "+", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Add.mCD(%s, %s)", true);
      insert(tyMaybeDouble, "+", tyInt, tyMaybeDouble, "LibArithmetic.Add.mDI(%s, %s)", false);
      insert(tyMaybeDouble, "+", tyLong, tyMaybeDouble, "LibArithmetic.Add.mDL(%s, %s)", false);
      insert(tyMaybeDouble, "+", tyDouble, tyMaybeDouble, "LibArithmetic.Add.mDD(%s, %s)", false);
      insert(tyMaybeDouble, "+", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Add.mDmD(%s, %s)", false);
      insert(tyMaybeDouble, "+", tyComplex, tyMaybeComplex, "LibArithmetic.Add.CmD(%s, %s)", true);
      insert(tyMaybeDouble, "+", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Add.mCmD(%s, %s)", true);
      insert(tyComplex, "+", tyInt, tyComplex, "LibArithmetic.Add.CI(%s, %s)", false);
      insert(tyComplex, "+", tyLong, tyComplex, "LibArithmetic.Add.CL(%s, %s)", false);
      insert(tyComplex, "+", tyDouble, tyComplex, "LibArithmetic.Add.CD(%s, %s)", false);
      insert(tyComplex, "+", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Add.CmD(%s, %s)", false);
      insert(tyComplex, "+", tyComplex, tyComplex, "LibArithmetic.Add.CC(%s, %s)", false);
      insert(tyComplex, "+", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Add.mCC(%s, %s)", true);
      insert(tyMaybeComplex, "+", tyInt, tyMaybeComplex, "LibArithmetic.Add.mCI(%s, %s)", false);
      insert(tyMaybeComplex, "+", tyLong, tyMaybeComplex, "LibArithmetic.Add.mCL(%s, %s)", false);
      insert(tyMaybeComplex, "+", tyDouble, tyMaybeComplex, "LibArithmetic.Add.mCD(%s, %s)", false);
      insert(tyMaybeComplex, "+", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Add.mCmD(%s, %s)", false);
      insert(tyMaybeComplex, "+", tyComplex, tyMaybeComplex, "LibArithmetic.Add.mCC(%s, %s)", false);
      insert(tyMaybeComplex, "+", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Add.mCmC(%s, %s)", false);
      // tyInt
      insert(tyInt, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyInt, tyString, "%s + %s", false);
      insert(tyInt, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyInt, tyString, "(%s).toString() + %s", false);
      // tyLong
      insert(tyLong, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyLong, tyString, "%s + %s", false);
      insert(tyLong, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyLong, tyString, "(%s).toString() + %s", false);
      // tyDouble
      insert(tyDouble, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyDouble, tyString, "%s + %s", false);
      insert(tyDouble, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyDouble, tyString, "(%s).toString() + %s", false);
      // tyMaybeDouble
      insert(tyMaybeDouble, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyMaybeDouble, tyString, "%s + %s", false);
      insert(tyMaybeDouble, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyMaybeDouble, tyString, "(%s).toString() + %s", false);
      // tyComplex
      insert(tyComplex, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyComplex, tyString, "%s + %s", false);
      insert(tyComplex, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyComplex, tyString, "(%s).toString() + %s", false);
      // tyMaybeComplex
      insert(tyMaybeComplex, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyMaybeComplex, tyString, "%s + %s", false);
      insert(tyMaybeComplex, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyMaybeComplex, tyString, "(%s).toString() + %s", false);
      // tyBoolean
      insert(tyBoolean, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyBoolean, tyString, "%s + %s", false);
      insert(tyBoolean, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyBoolean, tyString, "(%s).toString() + %s", false);
      // tyString
      insert(tyString, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyString, tyString, "(%s).toString() + %s", false);
      // tyMaybeString
      insert(tyMaybeString, "+", tyString, tyString, "%s + %s", false);
      insert(tyString, "+", tyMaybeString, tyString, "%s + %s", false);
      insert(tyMaybeString, "+", tyMaybeString, tyString, "%s + (%s).toString()", false);
      insert(tyMaybeString, "+", tyMaybeString, tyString, "(%s).toString() + %s", false);
    }
    // SUBTRACTION
    {
      insert(tyInt, "-", tyInt, tyInt, "%s - %s", false);
      insert(tyInt, "-", tyLong, tyLong, "%s - %s", false);
      insert(tyInt, "-", tyDouble, tyDouble, "%s - %s", false);
      insert(tyInt, "-", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Subtract.ImD(%s, %s)", false);
      insert(tyInt, "-", tyComplex, tyComplex, "LibArithmetic.Subtract.IC(%s, %s)", false);
      insert(tyInt, "-", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Subtract.ImC(%s, %s)", false);
      insert(tyLong, "-", tyInt, tyLong, "%s - %s", false);
      insert(tyLong, "-", tyLong, tyLong, "%s - %s", false);
      insert(tyLong, "-", tyDouble, tyDouble, "%s - %s", false);
      insert(tyLong, "-", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Subtract.LmD(%s, %s)", false);
      insert(tyLong, "-", tyComplex, tyComplex, "LibArithmetic.Subtract.LC(%s, %s)", false);
      insert(tyLong, "-", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Subtract.LmC(%s, %s)", false);
      insert(tyDouble, "-", tyInt, tyDouble, "%s - %s", false);
      insert(tyDouble, "-", tyLong, tyDouble, "%s - %s", false);
      insert(tyDouble, "-", tyDouble, tyDouble, "%s - %s", false);
      insert(tyDouble, "-", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Subtract.DmD(%s, %s)", false);
      insert(tyDouble, "-", tyComplex, tyComplex, "LibArithmetic.Subtract.DC(%s, %s)", false);
      insert(tyDouble, "-", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Subtract.DmC(%s, %s)", false);
      insert(tyMaybeDouble, "-", tyInt, tyMaybeDouble, "LibArithmetic.Subtract.mDI(%s, %s)", false);
      insert(tyMaybeDouble, "-", tyLong, tyMaybeDouble, "LibArithmetic.Subtract.mDL(%s, %s)", false);
      insert(tyMaybeDouble, "-", tyDouble, tyMaybeDouble, "LibArithmetic.Subtract.mDD(%s, %s)", false);
      insert(tyMaybeDouble, "-", tyMaybeDouble, tyMaybeDouble, "LibArithmetic.Subtract.mDmD(%s, %s)", false);
      insert(tyMaybeDouble, "-", tyComplex, tyMaybeComplex, "LibArithmetic.Subtract.mDC(%s, %s)", false);
      insert(tyMaybeDouble, "-", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Subtract.mDmC(%s, %s)", false);
      insert(tyComplex, "-", tyInt, tyComplex, "LibArithmetic.Subtract.CI(%s, %s)", false);
      insert(tyComplex, "-", tyLong, tyComplex, "LibArithmetic.Subtract.CL(%s, %s)", false);
      insert(tyComplex, "-", tyDouble, tyComplex, "LibArithmetic.Subtract.CD(%s, %s)", false);
      insert(tyComplex, "-", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Subtract.CmD(%s, %s)", false);
      insert(tyComplex, "-", tyComplex, tyComplex, "LibArithmetic.Subtract.CC(%s, %s)", false);
      insert(tyComplex, "-", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Subtract.CmC(%s, %s)", false);
      insert(tyMaybeComplex, "-", tyInt, tyMaybeComplex, "LibArithmetic.Subtract.mCI(%s, %s)", false);
      insert(tyMaybeComplex, "-", tyLong, tyMaybeComplex, "LibArithmetic.Subtract.mCL(%s, %s)", false);
      insert(tyMaybeComplex, "-", tyDouble, tyMaybeComplex, "LibArithmetic.Subtract.mCD(%s, %s)", false);
      insert(tyMaybeComplex, "-", tyMaybeDouble, tyMaybeComplex, "LibArithmetic.Subtract.mCmD(%s, %s)", false);
      insert(tyMaybeComplex, "-", tyComplex, tyMaybeComplex, "LibArithmetic.Subtract.mCC(%s, %s)", false);
      insert(tyMaybeComplex, "-", tyMaybeComplex, tyMaybeComplex, "LibArithmetic.Subtract.mCmC(%s, %s)", false);
    }
    // COMPARE and EQUALITY
    {
      insert(tyInt, "<", tyInt, tyBoolean, "%s < %s", false);
      insert(tyInt, "<", tyLong, tyBoolean, "%s < %s", false);
      insert(tyInt, "<", tyDouble, tyBoolean, "%s < %s", false);
      insert(tyLong, "<", tyInt, tyBoolean, "%s < %s", false);
      insert(tyLong, "<", tyLong, tyBoolean, "%s < %s", false);
      insert(tyLong, "<", tyDouble, tyBoolean, "%s < %s", false);
      insert(tyDouble, "<", tyInt, tyBoolean, "%s < %s", false);
      insert(tyDouble, "<", tyLong, tyBoolean, "%s < %s", false);
      insert(tyDouble, "<", tyDouble, tyBoolean, "%s < %s", false);
      insert(tyInt, "<=", tyInt, tyBoolean, "%s <= %s", false);
      insert(tyInt, "<=", tyLong, tyBoolean, "%s <= %s", false);
      insert(tyInt, "<=", tyDouble, tyBoolean, "%s <= %s", false);
      insert(tyLong, "<=", tyInt, tyBoolean, "%s <= %s", false);
      insert(tyLong, "<=", tyLong, tyBoolean, "%s <= %s", false);
      insert(tyLong, "<=", tyDouble, tyBoolean, "%s <= %s", false);
      insert(tyDouble, "<=", tyInt, tyBoolean, "%s <= %s", false);
      insert(tyDouble, "<=", tyLong, tyBoolean, "%s <= %s", false);
      insert(tyDouble, "<=", tyDouble, tyBoolean, "%s <= %s", false);
      insert(tyInt, "==", tyInt, tyBoolean, "%s == %s", false);
      insert(tyInt, "==", tyLong, tyBoolean, "%s == %s", false);
      insert(tyInt, "==", tyDouble, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyComplex, "==", tyInt, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyInt, "==", tyComplex, tyBoolean, "LibMath.near(%s, %s)", true);
      insert(tyLong, "==", tyInt, tyBoolean, "%s == %s", false);
      insert(tyLong, "==", tyLong, tyBoolean, "%s == %s", false);
      insert(tyLong, "==", tyDouble, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyComplex, "==", tyLong, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyLong, "==", tyComplex, tyBoolean, "LibMath.near(%s, %s)", true);
      insert(tyDouble, "==", tyInt, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyDouble, "==", tyLong, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyDouble, "==", tyDouble, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyComplex, "==", tyDouble, tyBoolean, "LibMath.near(%s, %s)", false);
      insert(tyDouble, "==", tyComplex, tyBoolean, "LibMath.near(%s, %s)", true);
      insert(tyInt, "!=", tyInt, tyBoolean, "%s != %s", false);
      insert(tyInt, "!=", tyLong, tyBoolean, "%s != %s", false);
      insert(tyInt, "!=", tyDouble, tyBoolean, "!LibMath.near(%s, %s)", false);
      insert(tyLong, "!=", tyInt, tyBoolean, "%s != %s", false);
      insert(tyLong, "!=", tyLong, tyBoolean, "%s != %s", false);
      insert(tyLong, "!=", tyDouble, tyBoolean, "!LibMath.near(%s, %s)", false);
      insert(tyDouble, "!=", tyInt, tyBoolean, "!LibMath.near(%s, %s)", false);
      insert(tyDouble, "!=", tyLong, tyBoolean, "!LibMath.near(%s, %s)", false);
      insert(tyDouble, "!=", tyDouble, tyBoolean, "!LibMath.near(%s, %s)", false);
      insert(tyInt, ">=", tyInt, tyBoolean, "%s >= %s", false);
      insert(tyInt, ">=", tyLong, tyBoolean, "%s >= %s", false);
      insert(tyInt, ">=", tyDouble, tyBoolean, "%s >= %s", false);
      insert(tyLong, ">=", tyInt, tyBoolean, "%s >= %s", false);
      insert(tyLong, ">=", tyLong, tyBoolean, "%s >= %s", false);
      insert(tyLong, ">=", tyDouble, tyBoolean, "%s >= %s", false);
      insert(tyDouble, ">=", tyInt, tyBoolean, "%s >= %s", false);
      insert(tyDouble, ">=", tyLong, tyBoolean, "%s >= %s", false);
      insert(tyDouble, ">=", tyDouble, tyBoolean, "%s >= %s", false);
      insert(tyInt, ">", tyInt, tyBoolean, "%s > %s", false);
      insert(tyInt, ">", tyLong, tyBoolean, "%s > %s", false);
      insert(tyInt, ">", tyDouble, tyBoolean, "%s > %s", false);
      insert(tyLong, ">", tyInt, tyBoolean, "%s > %s", false);
      insert(tyLong, ">", tyLong, tyBoolean, "%s > %s", false);
      insert(tyLong, ">", tyDouble, tyBoolean, "%s > %s", false);
      insert(tyDouble, ">", tyInt, tyBoolean, "%s > %s", false);
      insert(tyDouble, ">", tyLong, tyBoolean, "%s > %s", false);
      insert(tyDouble, ">", tyDouble, tyBoolean, "%s > %s", false);
      insert(tyString, "<", tyString, tyBoolean, "(%s).compareTo(%s) < 0", false);
      insert(tyString, "<=", tyString, tyBoolean, "(%s).compareTo(%s) <= 0", false);
      insert(tyString, "==", tyString, tyBoolean, "(%s).equals(%s)", false);
      insert(tyString, "!=", tyString, tyBoolean, "!((%s).equals(%s))", false);
      insert(tyString, ">=", tyString, tyBoolean, "(%s).compareTo(%s) >= 0", false);
      insert(tyString, ">", tyString, tyBoolean, "(%s).compareTo(%s) > 0", false);
      insert(tyLabel, "==", tyLabel, tyBoolean, "(%s).equals(%s)", false);
      insert(tyLabel, "!=", tyLabel, tyBoolean, "!((%s).equals(%s))", false);
      insert(tyAsset, "==", tyAsset, tyBoolean, "(%s).equals(%s)", false);
      insert(tyAsset, "!=", tyAsset, tyBoolean, "!((%s).equals(%s))", false);
      insert(tyClient, "==", tyClient, tyBoolean, "(%s).equals(%s)", false);
      insert(tyClient, "!=", tyClient, tyBoolean, "!((%s).equals(%s))", false);
    }
    // LOGIC
    {
      insert(tyBoolean, "&&", tyBoolean, tyBoolean, "%s && %s", false);
      insert(tyBoolean, "||", tyBoolean, tyBoolean, "%s || %s", false);
      insert(tyBoolean, "^^", tyBoolean, tyBoolean, "LibMath.xor(%s, %s)", false);
    }
    // MOD
    {
      insert(tyInt, "%", tyInt, tyMaybeInt, "LibArithmetic.Mod.O(%s, %s)", false);
      insert(tyLong, "%", tyInt, tyMaybeLong, "LibArithmetic.Mod.O(%s, %s)", false);
      insert(tyLong, "%", tyLong, tyMaybeLong, "LibArithmetic.Mod.O(%s, %s)", false);
    }
    // ADDITION-ASSIGNMENT (+=)
    {
      insert(tyRxInteger, "+=", tyInt, tyInt,"%s.opAddTo(%s)", false);
      insert(tyRxLong, "+=", tyInt, tyLong,"%s.opAddTo(%s)", false);
      insert(tyRxLong, "+=", tyLong, tyLong,"%s.opAddTo(%s)", false);
      insert(tyRxDouble, "+=", tyInt, tyDouble,"%s.opAddTo(%s)", false);
      insert(tyRxDouble, "+=", tyLong, tyDouble,"%s.opAddTo(%s)", false);
      insert(tyRxDouble, "+=", tyDouble, tyDouble,"%s.opAddTo(%s)", false);
      insert(tyRxComplex, "+=", tyInt, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyRxComplex, "+=", tyLong, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyRxComplex, "+=", tyDouble, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyRxComplex, "+=", tyComplex, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyBoolean, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyInt, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyLong, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyDouble, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyComplex, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyString, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyMaybeBoolean, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyMaybeInt, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyMaybeLong, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyMaybeDouble, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyMaybeComplex, tyString,"%s.opAddTo(%s)", false);
      insert(tyRxString, "+=", tyMaybeString, tyString,"%s.opAddTo(%s)", false);
      insert(tyInt, "+=", tyInt, tyInt,"%s += %s", false);
      insert(tyLong, "+=", tyInt, tyLong,"%s += %s", false);
      insert(tyLong, "+=", tyLong, tyLong,"%s += %s", false);
      insert(tyDouble, "+=", tyInt, tyDouble,"%s += %s", false);
      insert(tyDouble, "+=", tyLong, tyDouble,"%s += %s", false);
      insert(tyDouble, "+=", tyDouble, tyDouble,"%s += %s", false);
      insert(tyComplex, "+=", tyInt, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyComplex, "+=", tyLong, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyComplex, "+=", tyDouble, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyComplex, "+=", tyComplex, tyComplex,"%s.opAddTo(%s)", false);
      insert(tyString, "+=", tyBoolean, tyString,"%s += %s", false);
      insert(tyString, "+=", tyInt, tyString,"%s += %s", false);
      insert(tyString, "+=", tyLong, tyString,"%s += %s", false);
      insert(tyString, "+=", tyDouble, tyString,"%s += %s", false);
      insert(tyString, "+=", tyComplex, tyString,"%s += %s", false);
      insert(tyString, "+=", tyString, tyString,"%s += %s", false);
      insert(tyString, "+=", tyMaybeBoolean, tyString,"%s += %s", false);
      insert(tyString, "+=", tyMaybeInt, tyString,"%s += %s", false);
      insert(tyString, "+=", tyMaybeLong, tyString,"%s += %s", false);
      insert(tyString, "+=", tyMaybeDouble, tyString,"%s += %s", false);
      insert(tyString, "+=", tyMaybeComplex, tyString,"%s += %s", false);
      insert(tyString, "+=", tyMaybeString, tyString,"%s += %s", false);

      insert(tyListRxInteger, "+=", tyInt, tyInt, "LibArithmetic.ListMath.addToII(%s, %s)", false);
      insert(tyListRxLong, "+=", tyInt, tyLong, "LibArithmetic.ListMath.addToLI(%s, %s)", false);
      insert(tyListRxLong, "+=", tyLong, tyLong, "LibArithmetic.ListMath.addToLL(%s, %s)", false);
      insert(tyListRxDouble, "+=", tyInt, tyDouble, "LibArithmetic.ListMath.addToDI(%s, %s)", false);
      insert(tyListRxDouble, "+=", tyLong, tyDouble,"LibArithmetic.ListMath.addToDL(%s, %s)", false);
      insert(tyListRxDouble, "+=", tyDouble, tyDouble, "LibArithmetic.ListMath.addToDL(%s, %s)", false);
      insert(tyListRxComplex, "+=", tyInt, tyComplex,"LibArithmetic.ListMath.addToCI(%s, %s)", false);
      insert(tyListRxComplex, "+=", tyLong, tyComplex, "LibArithmetic.ListMath.addToCL(%s, %s)", false);
      insert(tyListRxComplex, "+=", tyDouble, tyComplex, "LibArithmetic.ListMath.addToCD(%s, %s)", false);
      insert(tyListRxComplex, "+=", tyComplex, tyComplex, "LibArithmetic.ListMath.addToCC(%s, %s)", false);
      insert(tyListRxString, "+=", tyBoolean, tyString, "LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyInt, tyString, "LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyLong, tyString, "LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyDouble, tyString, "LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyComplex, tyString, "LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyString, tyString, "LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyMaybeBoolean, tyString,"LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyMaybeInt, tyString,"LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyMaybeLong, tyString,"LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyMaybeDouble, tyString,"LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyMaybeComplex, tyString,"LibArithmetic.ListMath.addToSO(%s, %s)", false);
      insert(tyListRxString, "+=", tyMaybeString, tyString,"LibArithmetic.ListMath.addToSO(%s, %s)", false);
    }
    // SUBTRACTION-ASSIGNMENT (-=)
    {

    }
    // MULTIPLICATION-ASSIGNMENT (*=) {
    {

    }


  }

  private void insert(TyType left, String operator, TyType right, TyType result, String java, boolean reverse) {
    table.put(left.getAdamaType() + operator + right.getAdamaType(), new BinaryOperatorResult(result, java, reverse));
  }

  public BinaryOperatorResult find(TyType left, String operator, TyType right, Environment environment) {
    if (left != null && right != null) {
      String leftAdamaType = left.getAdamaType();
      String rightAdamaType = right.getAdamaType();
      BinaryOperatorResult result = table.get(leftAdamaType + operator + rightAdamaType);
      if (result == null) {
        environment.document.createError(DocumentPosition.sum(left, right), String.format("Could not find a meaning for '%s' %s '%s'", leftAdamaType, operator, rightAdamaType), "OperatorTable");
      }
      return result;
    }
    return null;
  }

  public static BinaryOperatorTable INSTANCE = new BinaryOperatorTable();
}
