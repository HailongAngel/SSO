package cn.diguo.sso.util;

import java.util.ResourceBundle;

/**
 * @program: demo
 * @description:
 * @author: Hailong
 * @create: 2018-11-13 14:32
 **/
public class RetMessageUtils {
    //返回数据成功
    public final static  String SUCCESS = "0000";

    //抛出异常
    public final static String EXCEPTION = "1111";

    //查询参数为空
    public final static String ERROR_QERUEST_PARAM = "9001";

    //没有查询到发票
    public final static String NO_LIST = "9012";
    //邮件发送异常
    public final static String  SEND_EXCEPTION= "9005";

    //添加/修改用户 参数验证相关提示
    public final static String USERNAME_INCORRENT = "5001";
    public final static String PASSWORD_INCORRENT = "5002";
    public final static String REALNAME_INCORRENT = "5003";
    public final static String PHONE_INCONRRENT = "5004";
    public final static String EMAIL_INCONRRENT = "5005";

    //删除组织 错误提示
    public static final String ZZ_ORGANIZATION_HAS_CHILD = "5061";
    public static final String ZZ_ORGANIZATION_HAS_PEOPLE = "5062";
    public static final String ZZ_ORGANIZATION_CANNOT_DELETE = "5063";

    //添加/修改组织 参数验证相关提示
    public static final String ZZ_BM_CANNOT_ADD_JG = "5070";
    public static final String ZZ_ORGANIZATION_CJ_TOO_DEPTH = "5071";
    public static final String ZZ_XFDM_INCORRENT = "5072";
    public static final String ZZ_XFDM_USED = "5073";
    public static final String ZZ_MC_INCORRENT = "5074";
    public static final String ZZ_MC_USED = "5075";



    //客户信息
    public static final String KH_BH_INCORRENT= "5130";
    public static final String KH_MC_INCORRENT = "5131";
    public static final String KH_SH_INCORRENT = "5132";
    public static final String KH_SH_USED = "5133";
    public static final String KH_DZDH_INCORRENT = "5134";
    public static final String KH_YHZH_INCORRENT = "5135";



    private static ResourceBundle resource = ResourceBundle.getBundle("message");

    /**
     * 获得资源
     *
     * @param messageKey 消息key
     * @return 消息内容
     */
    public static String getMessage(String messageKey)
    {
        return resource.getString(messageKey);
    }



    public static String getStackMsg(Exception e)
    {
        StackTraceElement[] stackArray = e.getStackTrace();
        if (stackArray != null && stackArray.length > 0)
        {
            StringBuffer stringBuffer;
            stringBuffer = new StringBuffer();
            for (StackTraceElement element : stackArray)
            {
                stringBuffer.append(element.toString());
                stringBuffer.append("\n");
            }
            return stringBuffer.toString();
        }
        return "";
    }

}
