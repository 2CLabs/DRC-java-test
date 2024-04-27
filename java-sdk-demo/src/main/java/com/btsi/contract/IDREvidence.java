package com.btsi.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class IDREvidence extends Contract {
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"dataHash\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"dataRight\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"metaData\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"variableData\",\"type\":\"string[]\"}],\"name\":\"addDataRightEvidence\",\"outputs\":[],\"selector\":[2635209621,3623663505],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"reviewerBid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"reviewDataHash\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"metaData\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"variableData\",\"type\":\"string[]\"}],\"name\":\"addReviewEvidence\",\"outputs\":[],\"selector\":[4024293368,766756406],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"usci\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"internalType\":\"string[]\",\"name\":\"roles\",\"type\":\"string[]\"}],\"name\":\"addUser\",\"outputs\":[],\"selector\":[3239424820,3652011643],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"variableData\",\"type\":\"string[]\"}],\"name\":\"appendVariableData\",\"outputs\":[],\"selector\":[2138450037,1301797456],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"disableAccessControl\",\"outputs\":[],\"selector\":[1980433629,2854924890],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"enableAccessControl\",\"outputs\":[],\"selector\":[922088542,1158894738],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getAccessControl\",\"outputs\":[{\"internalType\":\"uint32\",\"name\":\"status\",\"type\":\"uint32\"}],\"selector\":[4229803393,1904329564],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getChainName\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"}],\"selector\":[3609374908,728140679],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"getDataCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"dataCount\",\"type\":\"uint256\"}],\"selector\":[816278328,3023591269],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"start\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"name\":\"getDataList\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"udriArray\",\"type\":\"string[]\"}],\"selector\":[2492434596,1794719790],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getDataRightCategory\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"fields\",\"type\":\"string[]\"}],\"selector\":[2324699571,3796759802],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"}],\"name\":\"getRegisteredData\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"dataHashSM\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"dataHashSHA\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"dataRight\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"metaData\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"variableData\",\"type\":\"string[]\"},{\"internalType\":\"bool\",\"name\":\"isWithdraw\",\"type\":\"bool\"}],\"selector\":[208288129,3936625741],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"}],\"name\":\"getReviewCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"reviewCount\",\"type\":\"uint256\"}],\"selector\":[1485793629,1330250274],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"reviewerBid\",\"type\":\"string\"}],\"name\":\"getReviewCountOfReviewer\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"selector\":[3868489521,2826438872],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"category\",\"type\":\"string\"}],\"name\":\"getSupportVariableDataFields\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"fileds\",\"type\":\"string[]\"}],\"selector\":[3869446330,376683502],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getTextMaxLen\",\"outputs\":[{\"internalType\":\"uint32\",\"name\":\"len\",\"type\":\"uint32\"}],\"selector\":[3460282540,1588090095],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"dataHash\",\"type\":\"string\"}],\"name\":\"getUdriByDatahash\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"}],\"selector\":[633708824,3726531728],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"getUserDataRight\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"dataRight\",\"type\":\"string[]\"}],\"selector\":[1105228669,1195862205],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"getUserRoles\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"usci\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"roles\",\"type\":\"string[]\"}],\"selector\":[2573946725,1585755409],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"uint32\",\"name\":\"index\",\"type\":\"uint32\"}],\"name\":\"getVerifyEvidence\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"isWithdraw\",\"type\":\"bool\"},{\"internalType\":\"string\",\"name\":\"reviewerBid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"metaData\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"variableData\",\"type\":\"string[]\"}],\"selector\":[1855339097,4061585395],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"reviewerBid\",\"type\":\"string\"},{\"internalType\":\"uint32\",\"name\":\"index\",\"type\":\"uint32\"}],\"name\":\"getVerifyEvidenceOfReviewer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"isWithdraw\",\"type\":\"bool\"},{\"internalType\":\"string[]\",\"name\":\"metaData\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"variableData\",\"type\":\"string[]\"}],\"selector\":[1591648918,3508812136],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getstrArrayMaxLen\",\"outputs\":[{\"internalType\":\"uint32\",\"name\":\"len\",\"type\":\"uint32\"}],\"selector\":[905356897,1921885619],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"dataRight\",\"type\":\"string[]\"}],\"name\":\"grantUserDataRight\",\"outputs\":[],\"selector\":[3258251539,3358242835],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"grantUserManagePermission\",\"outputs\":[],\"selector\":[1037211317,3070963644],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"roles\",\"type\":\"string[]\"}],\"name\":\"grantUserRoles\",\"outputs\":[],\"selector\":[2322354691,115265356],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"hasUserManageRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1290417574,3048132719],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"queryUserRole\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"selector\":[289407936,1912013404],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"revokeUserManagePermission\",\"outputs\":[],\"selector\":[4233016450,2768200898],\"stateMutability\":\"nonpayable\",\"type\":\"func",
        "tion\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"roles\",\"type\":\"string[]\"}],\"name\":\"revokeUserRoles\",\"outputs\":[],\"selector\":[593762734,2271659916],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"}],\"name\":\"setChainName\",\"outputs\":[],\"selector\":[3234078498,3307895570],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"fields\",\"type\":\"string[]\"}],\"name\":\"setDataRightCategory\",\"outputs\":[],\"selector\":[2472945857,2087572871],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"category\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"fields\",\"type\":\"string[]\"}],\"name\":\"setDataRightSupportVariableDataFields\",\"outputs\":[],\"selector\":[1199116808,2135762870],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint32\",\"name\":\"len\",\"type\":\"uint32\"}],\"name\":\"setTextMaxLen\",\"outputs\":[],\"selector\":[1356933702,1285167137],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint32\",\"name\":\"len\",\"type\":\"uint32\"}],\"name\":\"setstrArrayMaxLen\",\"outputs\":[],\"selector\":[453424020,3279923519],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"dataRight\",\"type\":\"string[]\"}],\"name\":\"withdrawDataRightRegister\",\"outputs\":[],\"selector\":[2392051885,1179902888],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"reviewerBid\",\"type\":\"string\"}],\"name\":\"withdrawReviewEvidence\",\"outputs\":[],\"selector\":[932021754,2229502562],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"dataRight\",\"type\":\"string[]\"}],\"name\":\"withdrawUserDataRight\",\"outputs\":[],\"selector\":[4211774851,2825282343],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_ADDDATARIGHTEVIDENCE = "addDataRightEvidence";

    public static final String FUNC_ADDREVIEWEVIDENCE = "addReviewEvidence";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_APPENDVARIABLEDATA = "appendVariableData";

    public static final String FUNC_DISABLEACCESSCONTROL = "disableAccessControl";

    public static final String FUNC_ENABLEACCESSCONTROL = "enableAccessControl";

    public static final String FUNC_GETACCESSCONTROL = "getAccessControl";

    public static final String FUNC_GETCHAINNAME = "getChainName";

    public static final String FUNC_GETDATACOUNT = "getDataCount";

    public static final String FUNC_GETDATALIST = "getDataList";

    public static final String FUNC_GETDATARIGHTCATEGORY = "getDataRightCategory";

    public static final String FUNC_GETREGISTEREDDATA = "getRegisteredData";

    public static final String FUNC_GETREVIEWCOUNT = "getReviewCount";

    public static final String FUNC_GETREVIEWCOUNTOFREVIEWER = "getReviewCountOfReviewer";

    public static final String FUNC_GETSUPPORTVARIABLEDATAFIELDS = "getSupportVariableDataFields";

    public static final String FUNC_GETTEXTMAXLEN = "getTextMaxLen";

    public static final String FUNC_GETUDRIBYDATAHASH = "getUdriByDatahash";

    public static final String FUNC_GETUSERDATARIGHT = "getUserDataRight";

    public static final String FUNC_GETUSERROLES = "getUserRoles";

    public static final String FUNC_GETVERIFYEVIDENCE = "getVerifyEvidence";

    public static final String FUNC_GETVERIFYEVIDENCEOFREVIEWER = "getVerifyEvidenceOfReviewer";

    public static final String FUNC_GETSTRARRAYMAXLEN = "getstrArrayMaxLen";

    public static final String FUNC_GRANTUSERDATARIGHT = "grantUserDataRight";

    public static final String FUNC_GRANTUSERMANAGEPERMISSION = "grantUserManagePermission";

    public static final String FUNC_GRANTUSERROLES = "grantUserRoles";

    public static final String FUNC_HASUSERMANAGEROLE = "hasUserManageRole";

    public static final String FUNC_QUERYUSERROLE = "queryUserRole";

    public static final String FUNC_REVOKEUSERMANAGEPERMISSION = "revokeUserManagePermission";

    public static final String FUNC_REVOKEUSERROLES = "revokeUserRoles";

    public static final String FUNC_SETCHAINNAME = "setChainName";

    public static final String FUNC_SETDATARIGHTCATEGORY = "setDataRightCategory";

    public static final String FUNC_SETDATARIGHTSUPPORTVARIABLEDATAFIELDS =
            "setDataRightSupportVariableDataFields";

    public static final String FUNC_SETTEXTMAXLEN = "setTextMaxLen";

    public static final String FUNC_SETSTRARRAYMAXLEN = "setstrArrayMaxLen";

    public static final String FUNC_WITHDRAWDATARIGHTREGISTER = "withdrawDataRightRegister";

    public static final String FUNC_WITHDRAWREVIEWEVIDENCE = "withdrawReviewEvidence";

    public static final String FUNC_WITHDRAWUSERDATARIGHT = "withdrawUserDataRight";

    protected IDREvidence(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public TransactionReceipt addDataRightEvidence(
            String udri,
            String bid,
            List<String> dataHash,
            List<String> dataRight,
            List<String> metaData,
            List<String> variableData) {
        final Function function =
                new Function(
                        FUNC_ADDDATARIGHTEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataHash,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                metaData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForAddDataRightEvidence(
            String udri,
            String bid,
            List<String> dataHash,
            List<String> dataRight,
            List<String> metaData,
            List<String> variableData) {
        final Function function =
                new Function(
                        FUNC_ADDDATARIGHTEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataHash,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                metaData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String addDataRightEvidence(
            String udri,
            String bid,
            List<String> dataHash,
            List<String> dataRight,
            List<String> metaData,
            List<String> variableData,
            TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_ADDDATARIGHTEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataHash,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                metaData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple6<String, String, List<String>, List<String>, List<String>, List<String>>
            getAddDataRightEvidenceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_ADDDATARIGHTEVIDENCE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple6<String, String, List<String>, List<String>, List<String>, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()),
                convertToNative((List<Utf8String>) results.get(3).getValue()),
                convertToNative((List<Utf8String>) results.get(4).getValue()),
                convertToNative((List<Utf8String>) results.get(5).getValue()));
    }

    public TransactionReceipt addReviewEvidence(
            String udri,
            String reviewerBid,
            List<String> reviewDataHash,
            List<String> metaData,
            List<String> variableData) {
        final Function function =
                new Function(
                        FUNC_ADDREVIEWEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                reviewDataHash,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                metaData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForAddReviewEvidence(
            String udri,
            String reviewerBid,
            List<String> reviewDataHash,
            List<String> metaData,
            List<String> variableData) {
        final Function function =
                new Function(
                        FUNC_ADDREVIEWEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                reviewDataHash,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                metaData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String addReviewEvidence(
            String udri,
            String reviewerBid,
            List<String> reviewDataHash,
            List<String> metaData,
            List<String> variableData,
            TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_ADDREVIEWEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                reviewDataHash,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                metaData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple5<String, String, List<String>, List<String>, List<String>>
            getAddReviewEvidenceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_ADDREVIEWEVIDENCE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<String, String, List<String>, List<String>, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()),
                convertToNative((List<Utf8String>) results.get(3).getValue()),
                convertToNative((List<Utf8String>) results.get(4).getValue()));
    }

    public TransactionReceipt addUser(
            String bid, String usci, String name, String account, List<String> roles) {
        final Function function =
                new Function(
                        FUNC_ADDUSER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(usci),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(name),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForAddUser(
            String bid, String usci, String name, String account, List<String> roles) {
        final Function function =
                new Function(
                        FUNC_ADDUSER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(usci),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(name),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String addUser(
            String bid,
            String usci,
            String name,
            String account,
            List<String> roles,
            TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_ADDUSER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(usci),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(name),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple5<String, String, String, String, List<String>> getAddUserInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_ADDUSER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<String, String, String, String, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (String) results.get(2).getValue(),
                (String) results.get(3).getValue(),
                convertToNative((List<Utf8String>) results.get(4).getValue()));
    }

    public TransactionReceipt appendVariableData(String udri, List<String> variableData) {
        final Function function =
                new Function(
                        FUNC_APPENDVARIABLEDATA,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForAppendVariableData(
            String udri, List<String> variableData) {
        final Function function =
                new Function(
                        FUNC_APPENDVARIABLEDATA,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String appendVariableData(
            String udri, List<String> variableData, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_APPENDVARIABLEDATA,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                variableData,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, List<String>> getAppendVariableDataInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_APPENDVARIABLEDATA,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, List<String>>(
                (String) results.get(0).getValue(),
                convertToNative((List<Utf8String>) results.get(1).getValue()));
    }

    public TransactionReceipt disableAccessControl() {
        final Function function =
                new Function(
                        FUNC_DISABLEACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForDisableAccessControl() {
        final Function function =
                new Function(
                        FUNC_DISABLEACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String disableAccessControl(TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_DISABLEACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public TransactionReceipt enableAccessControl() {
        final Function function =
                new Function(
                        FUNC_ENABLEACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForEnableAccessControl() {
        final Function function =
                new Function(
                        FUNC_ENABLEACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String enableAccessControl(TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_ENABLEACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public BigInteger getAccessControl() throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETACCESSCONTROL,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public String getChainName() throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETCHAINNAME,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public BigInteger getDataCount(String bid) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETDATACOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public List getDataList(String bid, BigInteger start, BigInteger count)
            throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETDATALIST,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(start),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(count)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public List getDataRightCategory() throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETDATARIGHTCATEGORY,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public Tuple6<String, String, List<String>, List<String>, List<String>, Boolean>
            getRegisteredData(String udri) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETREGISTEREDDATA,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<Bool>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple6<String, String, List<String>, List<String>, List<String>, Boolean>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()),
                convertToNative((List<Utf8String>) results.get(3).getValue()),
                convertToNative((List<Utf8String>) results.get(4).getValue()),
                (Boolean) results.get(5).getValue());
    }

    public BigInteger getReviewCount(String udri) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETREVIEWCOUNT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger getReviewCountOfReviewer(String udri, String reviewerBid)
            throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETREVIEWCOUNTOFREVIEWER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public List getSupportVariableDataFields(String category) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETSUPPORTVARIABLEDATAFIELDS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(category)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public BigInteger getTextMaxLen() throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETTEXTMAXLEN,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public String getUdriByDatahash(String dataHash) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETUDRIBYDATAHASH,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(dataHash)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public List getUserDataRight(String udri, String bid) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public Tuple3<String, String, List<String>> getUserRoles(String bid) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple3<String, String, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()));
    }

    public Tuple4<Boolean, String, List<String>, List<String>> getVerifyEvidence(
            String udri, BigInteger index) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETVERIFYEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(index)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Bool>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple4<Boolean, String, List<String>, List<String>>(
                (Boolean) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()),
                convertToNative((List<Utf8String>) results.get(3).getValue()));
    }

    public Tuple3<Boolean, List<String>, List<String>> getVerifyEvidenceOfReviewer(
            String udri, String reviewerBid, BigInteger index) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETVERIFYEVIDENCEOFREVIEWER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(index)),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Bool>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple3<Boolean, List<String>, List<String>>(
                (Boolean) results.get(0).getValue(),
                convertToNative((List<Utf8String>) results.get(1).getValue()),
                convertToNative((List<Utf8String>) results.get(2).getValue()));
    }

    public BigInteger getstrArrayMaxLen() throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETSTRARRAYMAXLEN,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt grantUserDataRight(String udri, String bid, List<String> dataRight) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantUserDataRight(
            String udri, String bid, List<String> dataRight) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String grantUserDataRight(
            String udri, String bid, List<String> dataRight, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple3<String, String, List<String>> getGrantUserDataRightInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTUSERDATARIGHT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()));
    }

    public TransactionReceipt grantUserManagePermission(String bid, String account) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantUserManagePermission(String bid, String account) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String grantUserManagePermission(
            String bid, String account, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, String> getGrantUserManagePermissionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public TransactionReceipt grantUserRoles(String bid, List<String> roles) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantUserRoles(String bid, List<String> roles) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String grantUserRoles(String bid, List<String> roles, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, List<String>> getGrantUserRolesInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTUSERROLES,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, List<String>>(
                (String) results.get(0).getValue(),
                convertToNative((List<Utf8String>) results.get(1).getValue()));
    }

    public Boolean hasUserManageRole(String bid) throws ContractException {
        final Function function =
                new Function(
                        FUNC_HASUSERMANAGEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public List queryUserRole() throws ContractException {
        final Function function =
                new Function(
                        FUNC_QUERYUSERROLE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> result = (List<Type>) executeCallWithSingleValueReturn(function, List.class);
        return convertToNative(result);
    }

    public TransactionReceipt revokeUserManagePermission(String bid) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRevokeUserManagePermission(String bid) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String revokeUserManagePermission(String bid, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<String> getRevokeUserManagePermissionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public TransactionReceipt revokeUserRoles(String bid, List<String> roles) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRevokeUserRoles(String bid, List<String> roles) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String revokeUserRoles(String bid, List<String> roles, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERROLES,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                roles,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, List<String>> getRevokeUserRolesInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKEUSERROLES,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, List<String>>(
                (String) results.get(0).getValue(),
                convertToNative((List<Utf8String>) results.get(1).getValue()));
    }

    public TransactionReceipt setChainName(String name) {
        final Function function =
                new Function(
                        FUNC_SETCHAINNAME,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(name)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetChainName(String name) {
        final Function function =
                new Function(
                        FUNC_SETCHAINNAME,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(name)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setChainName(String name, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETCHAINNAME,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(name)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<String> getSetChainNameInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETCHAINNAME,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public TransactionReceipt setDataRightCategory(List<String> fields) {
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTCATEGORY,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                fields,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetDataRightCategory(List<String> fields) {
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTCATEGORY,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                fields,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setDataRightCategory(List<String> fields, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTCATEGORY,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                fields,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<List<String>> getSetDataRightCategoryInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTCATEGORY,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<List<String>>(
                convertToNative((List<Utf8String>) results.get(0).getValue()));
    }

    public TransactionReceipt setDataRightSupportVariableDataFields(
            String category, List<String> fields) {
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTSUPPORTVARIABLEDATAFIELDS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(category),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                fields,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetDataRightSupportVariableDataFields(
            String category, List<String> fields) {
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTSUPPORTVARIABLEDATAFIELDS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(category),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                fields,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setDataRightSupportVariableDataFields(
            String category, List<String> fields, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTSUPPORTVARIABLEDATAFIELDS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(category),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                fields,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, List<String>> getSetDataRightSupportVariableDataFieldsInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETDATARIGHTSUPPORTVARIABLEDATAFIELDS,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, List<String>>(
                (String) results.get(0).getValue(),
                convertToNative((List<Utf8String>) results.get(1).getValue()));
    }

    public TransactionReceipt setTextMaxLen(BigInteger len) {
        final Function function =
                new Function(
                        FUNC_SETTEXTMAXLEN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(len)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetTextMaxLen(BigInteger len) {
        final Function function =
                new Function(
                        FUNC_SETTEXTMAXLEN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(len)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setTextMaxLen(BigInteger len, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETTEXTMAXLEN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(len)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<BigInteger> getSetTextMaxLenInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETTEXTMAXLEN,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public TransactionReceipt setstrArrayMaxLen(BigInteger len) {
        final Function function =
                new Function(
                        FUNC_SETSTRARRAYMAXLEN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(len)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetstrArrayMaxLen(BigInteger len) {
        final Function function =
                new Function(
                        FUNC_SETSTRARRAYMAXLEN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(len)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setstrArrayMaxLen(BigInteger len, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETSTRARRAYMAXLEN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32(len)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<BigInteger> getSetstrArrayMaxLenInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETSTRARRAYMAXLEN,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>((BigInteger) results.get(0).getValue());
    }

    public TransactionReceipt withdrawDataRightRegister(String udri, List<String> dataRight) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWDATARIGHTREGISTER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForWithdrawDataRightRegister(
            String udri, List<String> dataRight) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWDATARIGHTREGISTER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String withdrawDataRightRegister(
            String udri, List<String> dataRight, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWDATARIGHTREGISTER,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, List<String>> getWithdrawDataRightRegisterInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_WITHDRAWDATARIGHTREGISTER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, List<String>>(
                (String) results.get(0).getValue(),
                convertToNative((List<Utf8String>) results.get(1).getValue()));
    }

    public TransactionReceipt withdrawReviewEvidence(String udri, String reviewerBid) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWREVIEWEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForWithdrawReviewEvidence(String udri, String reviewerBid) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWREVIEWEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String withdrawReviewEvidence(
            String udri, String reviewerBid, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWREVIEWEVIDENCE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(reviewerBid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, String> getWithdrawReviewEvidenceInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_WITHDRAWREVIEWEVIDENCE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public TransactionReceipt withdrawUserDataRight(
            String udri, String bid, List<String> dataRight) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForWithdrawUserDataRight(
            String udri, String bid, List<String> dataRight) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String withdrawUserDataRight(
            String udri, String bid, List<String> dataRight, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_WITHDRAWUSERDATARIGHT,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(udri),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                dataRight,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple3<String, String, List<String>> getWithdrawUserDataRightInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_WITHDRAWUSERDATARIGHT,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Utf8String>() {},
                                new TypeReference<DynamicArray<Utf8String>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, List<String>>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()));
    }

    public static IDREvidence load(
            String contractAddress, Client client, CryptoKeyPair credential) {
        return new IDREvidence(contractAddress, client, credential);
    }

    public static IDREvidence deploy(Client client, CryptoKeyPair credential)
            throws ContractException {
        return deploy(
                IDREvidence.class,
                client,
                credential,
                getBinary(client.getCryptoSuite()),
                getABI(),
                null,
                null);
    }
}
