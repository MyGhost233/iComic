package com.qiuchenly.comicparse.Http.Dmzj;

import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;

public class ComicApi {
    public static String a(int mType, String... strArr) {
        String str = "";
        switch (mType) {
            case 1:
                return "https://user.dmzj.com/register/verify";
            case 2:
                return "https://user.dmzj.com/register/m_submit_v2";
            case 3:
                return "https://user.dmzj.com/register/send_tel_code" + b(strArr);
            case 4:
                return "https://v3api.dmzj.com/account/sendtelcode" + b(strArr);
            case 5:
                return "https://v3api.dmzj.com/account/sendemailcode" + b(strArr);
            case 6:
                return "https://v3api.dmzj.com/account/isbindtelpwd?" + a(strArr);
            case 7:
                return "https://v3api.dmzj.com/account/regainedpwdbytel";
            case 8:
                return "https://v3api.dmzj.com/account/bindtel";
            case 9:
                return "https://v3api.dmzj.com/account/bindemailbypwd";
            case 10:
                return "https://v3api.dmzj.com/account/setpwdbytel";
            case 11:
                return "https://v3api.dmzj.com/account/setpwdbyemail";
            case 12:
                return "https://v3api.dmzj.com/account/setpwdbyoldpwd";
            case 13:
                return "https://v3api.dmzj.com/account/bindthirdparty";
            case 14:
                return "https://v3api.dmzj.com/account/bindemail";
            case 15:
                return "https://v3api.dmzj.com/account/verifyemail";
            case 16:
                return "https://v3api.dmzj.com/account/verifybindtel?" + a(strArr);
            case 17:
                return "https://v3api.dmzj.com/account/verifybindemail?" + a(strArr);
            case 18:
                return "https://v3api.dmzj.com/account/regainedpwdbyemail";
            case 19:
                return "https://api.weixin.qq.com/sns/oauth2/access_token";
            case 20:
                return "https://user.dmzj.com/loginV2/m_confirm";
            case MotionEventCompat.AXIS_WHEEL /*21*/:
                return "https://user.dmzj.com/loginV2/three_confirm";
            case 22:
                return "https://user.dmzj.com/login/again_register";
            case MotionEventCompat.AXIS_BRAKE /*23*/:
                return "https://v3api.dmzj.com/v3/recommend.json";
            case MotionEventCompat.AXIS_DISTANCE /*24*/:
                return "https://v3api.dmzj.com/latest/" + b(strArr) + ".json";
            case MotionEventCompat.AXIS_TILT /*25*/:
                return "https://v3api.dmzj.com/0/category.json";
            case 26:
                return "https://v3api.dmzj.com/subject/" + b(strArr) + ".json";
            case 27:
                return "https://v3api.dmzj.com/comic/" + b(strArr) + ".json";
            case 28:
                return "https://v3api.dmzj.com/recommend/batchUpdate";
            case 29:
                return "https://v3api.dmzj.com/classify/filter.json";
            case 30 /*30*/:
                return "https://v3api.dmzj.com/classify/" + b(strArr) + ".json";
            case 31:
                return "https://v3api.dmzj.com/rank/type_filter.json";
            case 32:
                return "https://v3api.dmzj.com/rank/" + b(strArr) + ".json";
            case 33:
                return "https://v3api.dmzj.com/subject/category.json";
            case 34:
                return "https://v3api.dmzj.com/subject/" + b(strArr) + ".json";
            case 35:
                return "https://v3api.dmzj.com/comment/" + b(strArr) + ".json";
            case 36:
                return "https://v3api.dmzj.com/common/report";
            case 37 /*37*/:
                return "https://v3api.dmzj.com/comment/add";
            case 38/*38*/:
                return "https://v3api.dmzj.com/comment/agree";
            case 39 /*39*/:
                return "https://v3api.dmzj.com/comment/total/" + b(strArr) + ".json";
            case 40/*40*/:
                return "https://v3api.dmzj.com/comment/owner/" + b(strArr) + ".json";
            case 41/*41*/:
                return "https://v3api.dmzj.com/comment/detail/" + b(strArr) + ".json";
            case 42 /*42*/:
                return "https://imgzip.dmzj.com/" + b(strArr) + ".zip";
            case 43 /*43*/:
                return "https://v3api.dmzj.com/article/category.json";
            case 443 /*44*/:
                return "https://v3api.dmzj.com/v3/article/recommend/header.json";
            case 45 /*45*/:
                return "https://v3api.dmzj.com/v3/article/list/" + b(strArr) + ".json";
            case 46 /*46*/:
                return "https://v3api.dmzj.com/article/mood/" + b(strArr);
            case 47 /*47*/:
                return "https://interface.dmzj.com/api/news/subscribe/add" + b(strArr);
            case 48 /*48*/:
                return "https://interface.dmzj.com/api/news/subscribe/check" + b(strArr);
            case 49:
                return "https://v3api.dmzj.com/v3/article/total/" + b(strArr) + ".json";
            case 50:
                return "https://interface.dmzj.com/api/news/subscribe/del" + b(strArr);
            case 51:
                return "https://interface.dmzj.com/api/news/getSubscribe" + b(strArr);
            case 52:
                return "https://v3api.dmzj.com/message/list/" + b(strArr) + ".json";
            case 53:
                return "https://v3api.dmzj.com/viewPoint/" + b(strArr) + ".json";
            case 54:
                return "https://v3api.dmzj.com/viewPoint/addv2";
            case 55:
                return "https://v3api.dmzj.com/viewPoint/praise";
            case 56:
                return "https://v3api.dmzj.com/hotView/" + b(strArr) + ".json";
            case 57:
                return "https://v3api.dmzj.com/novel/recentUpdate/" + b(strArr) + ".json";
            case 58:
                return "https://v3api.dmzj.com/novel/recommend.json";
            case 59:
                return "https://v3api.dmzj.com/1/category.json";
            case 60 /*60*/:
                return "https://v3api.dmzj.com/novel/filter.json";
            case 61:
                return "https://v3api.dmzj.com/novel/" + b(strArr) + ".json";
            case 62:
                return "https://v3api.dmzj.com/novel/" + b(strArr) + ".json";
            case 63:
                return "https://sacg.dmzj.com/lnovelsum/hit.php?" + a(strArr);
            case 64:
                return "https://v3api.dmzj.com/novel/chapter/" + b(strArr) + ".json";
            case 65:
                return "https://v3api.dmzj.com/novel/tag.json";
            case 66:
                return "https://v3api.dmzj.com/novel/rank/" + b(strArr) + ".json";
            case 67:
                return "https://v3api.dmzj.com/novel/download/" + b(strArr) + ".txt";
            case 68:
                return "https://v3api.dmzj.com/novel/collection/" + b(strArr) + ".json";
            case 69:
                return "https://v3api.dmzj.com/novel/collection/" + b(strArr) + ".json";
            case 70:
                return "https://api.dmzj.com/dynamic/comicversion/android";
            case 71:
                return "https://v3api.dmzj.com/bookList/" + b(strArr) + ".json";
            case 72:
                return "https://v3api.dmzj.com/bookList/collection/" + b(strArr) + ".json";
            case 73:
                return "https://v3api.dmzj.com/v3/subscribe/" + b(strArr) + ".json";
            case 74:
                return "https://v3api.dmzj.com/UCenter/comics/" + b(strArr);
            case 75:
                return "https://v3api.dmzj.com/UCenter/comicsv2/" + b(strArr);
            case 76:
                return "https://v3api.dmzj.com/UCenter/author/" + b(strArr) + ".json";
            case 77:
                return "https://v3api.dmzj.com/UCenter/subscribe";
            case 78:
                return "https://v3api.dmzj.com/bookList/collection/" + b(strArr) + ".json";
            case 79:
                return "https://v3api.dmzj.com/subscribe/add";
            case 80:
                return "https://v3api.dmzj.com/subscribe/cancel";
            case 81:
                return "https://v3api.dmzj.com/subscribe/" + b(strArr);
            case 82:
                return "https://v3api.dmzj.com/subscribe/read";
            case 83:
                str = b(strArr);
                String str2 = "https://v3api.dmzj.com/v3/comic/related/" + str + ".json";
                if ("".equals(str)) {
                   // b.c().a((m) new m("URLPathMaker.getUrlWithInnerParam").a("url", str2));
                }
                return str2;
            case 84:
                return "https://v3api.dmzj.com/chapter/" + b(strArr) + ".json";
            case 85:
                return "https://v3api.dmzj.com/v3/2/category.json";
            case 86:
                return "https://v3api.dmzj.com/search/fuzzy/" + b(strArr) + ".json";
            case 87:
                return "https://v3api.dmzj.com/search/show/" + b(strArr) + ".json";
            case 88:
                return "https://v3api.dmzj.com/search/hot/" + b(strArr) + ".json";
            case 89:
                return "https://v3api.dmzj.com/old/comment/" + b(strArr) + ".json";
            case 90:
                return "https://v3api.dmzj.com/old/comment/detail/" + b(strArr) + ".json";
            case 91:
                return "https://v3api.dmzj.com/old/comment/total/" + b(strArr) + ".json";
            case 92:
                return "https://v3api.dmzj.com/old/comment/add";
            case 93:
                return "https://v3api.dmzj.com/old/comment/agree";
            case 94:
                return "https://v3comment.dmzj.com/v1/" + b(strArr);
            case 95:
                return "https://v3api.dmzj.com/device/add";
            case 96:
                return "https://v3api.dmzj.com/device/building";
            case 97:
                return "https://v3api.dmzj.com/device/cancel";
            case 98:
                return "https://v3api.dmzj.com/account/cookie";
            case 99:
                return "https://v3api.dmzj.com/v3/old/comment/owner/" + b(strArr) + ".json";
            case 100:
                return "https://api.dmzj.com/dynamic/comichitsv2";
            case 101 /*101*/:
                return "https://v3api.dmzj.com/device/push";
            case 102 /*102*/:
                return "https://api.dmzj.com/dynamic/activity";
            case 103 /*103*/:
                return "https://v3api.dmzj.com/account/update";
            case 104 /*104*/:
                return "https://v3api.dmzj.com/account/modifyname";
            case 105 /*105*/:
                return "https://interface.dmzj.com/api/record/getRe";
            case 106 /*106*/:
                return "https://interface.dmzj.com/api/getReInfo/" + b(strArr);
            case 107 /*107*/:
                return "https://interface.dmzj.com/api/record/delRecords";
            case 108 /*108*/:
                return "https://v3comment.dmzj.com/v1/" + b(strArr);
            case 109 /*109*/:
                return "https://v3api.dmzj.com/msg/works/";
            case 110 /*110*/:
                return "https://v3api.dmzj.com/msg/worksComment/";
            case 111 /*111*/:
                return "https://v3api.dmzj.com/comment2/" + b(strArr) + ".json";
            case 112 /*112*/:
                return "https://v3comment.dmzj.com/v1/" + b(strArr);
            case 113 /*113*/:
                return "https://v3api.dmzj.com/comment2/getTopComment/" + b(strArr) + ".json";
            case 114:
                return "https://v3api.dmzj.com/msg/replyMe/" + b(strArr);
            case 115:
                return "https://v3api.dmzj.com/msg/msgList/" + b(strArr);
            case 116:
                return "https://v3api.dmzj.com/msg/chat/" + b(strArr);
            case 117:
                return "https://v3api.dmzj.com/msg/sendMessage/";
            case 118:
                return "https://v3api.dmzj.com/msg/delMessage/";
            case 119:
                return "https://v3api.dmzj.com/msg/msgStatuChange/";
            case 120/*120*/:
                return "https://v3api.dmzj.com/msg/changeReply/";
            case 121:
                return "https://v3api.dmzj.com/msg/unread/";
            case 122:
                return "https://v3api.dmzj.com/msg/msgShield/";
            case 123:
                return "https://v3api.dmzj.com/msg/delShield/";
            case 124:
                return "https://v3api.dmzj.com/comment2/commentReport";
            case 125:
                return "https://v3api.dmzj.com/msg/shieldList/" + b(strArr);
            case 126:
                return "https://v3api.dmzj.com/comment2/gagcheckv2/" + b(strArr) + ".json";
            case 127 /*127*/:
                return "https://v3api.dmzj.com/game_c/game/" + b(strArr) + ".json";
            case 128:
                return "https://v3api.dmzj.com/game_c.json";
            case 129:
                return "https://v3api.dmzj.com/game_c/game/detail/" + b(strArr) + ".json";
            case 130 /*130*/:
                return "https://v3api.dmzj.com/game_c/gift/" + b(strArr) + ".json";
            case 131:
                return "https://v3api.dmzj.com/game_c/gift/hasgift/" + b(strArr) + ".json";
            case 132:
                return "https://v3api.dmzj.com/game_c/gift/detail/" + b(strArr) + ".json";
            case 133:
                return "https://v3api.dmzj.com/game_c/gift/hascode/" + b(strArr) + ".json";
            case 134:
                return "https://v3api.dmzj.com/game_c/gift/getcode/";
            case 135:
                return "https://v3api.dmzj.com/game_c/game/downcount";
            default:
                return str;
        }
    }


    private static String a(String... strArr) {
        String str = "";
        if (strArr == null || strArr.length == 0) {
            return str;
        }
        String str2;
        int i = 0;
        while (true) {
            try {
                int i2 = i;
                str2 = str;
                if (i2 >= strArr.length) {
                    break;
                }
                str = i2 != 0 ? str2 + "&" + strArr[i2].trim() : str2 + strArr[i2].trim();
                i = i2 + 1;
            } catch (Exception e) {
                e.printStackTrace();
                //b.c().a((m) new m("URLPathMaker.getUrlWithInnerParam").a("paramsStr", str2));
            }
        }
        return str2;
    }

    public static boolean a(String str) {
        return TextUtils.isEmpty(str) || str.contains("https://v3api.dmzj.com/comment2/gagcheckv2/") || str.contains("https://v3api.dmzj.com/comment2/getTopComment/") || str.contains("https://v3api.dmzj.com/comment2/") || str.contains("https://v3api.dmzj.com/comment/owner/") || str.contains("https://v3api.dmzj.com/comment/detail/") || str.contains("https://v3api.dmzj.com/v3/old/comment/owner/") || str.contains("https://v3api.dmzj.com/msg/worksComment/") || str.contains("https://v3comment.dmzj.com/v1/0/latest/") || str.contains("https://v3comment.dmzj.com/v1/1/latest/") || str.contains("https://v3comment.dmzj.com/v1/2/latest/") || str.contains("https://v3comment.dmzj.com/v1/3/latest/") || str.contains("https://v3comment.dmzj.com/v1/4/latest/") || str.contains("https://v3comment.dmzj.com/v1/5/latest/") || str.contains("https://v3comment.dmzj.com/v1/6/latest/") || str.contains("https://v3comment.dmzj.com/v1/7/latest/") || str.contains("https://v3comment.dmzj.com/v1/8/latest/") || str.contains("https://v3comment.dmzj.com/v1/9/latest/") || str.contains("https://v3comment.dmzj.com/v1/10/latest/");
    }

    private static String b(String... strArr) {
        String str = "";
        if (strArr == null || strArr.length == 0) {
            return str;
        }
        String str2;
        int i = 0;
        while (true) {
            try {
                int i2 = i;
                str2 = str;
                if (i2 >= strArr.length) {
                    break;
                }
                str = i2 != 0 ? str2 + "/" + strArr[i2].trim() : str2 + strArr[i2].trim();
                i = i2 + 1;
            } catch (Exception e) {
                e.printStackTrace();
                //b.c().a((m) new m("URLPathMaker.getUrlWithInnerParam").a("paramsStr", str2));
            }
        }
        return str2;
    }
}
