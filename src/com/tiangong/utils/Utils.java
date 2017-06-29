
package com.tiangong.utils;

import android.content.Context;
import android.util.TypedValue;

public class Utils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context
                .getResources()
                .getDisplayMetrics());
    }

    public static enum RemoteType {
        RemoteTypeShutDown, // 关机
        RemoteTypeStart, // 开机
        RemoteTypePlay,
        RemoteTypePause,
        RemoteTypeStop,
        RemoteTypeVolumeUp, // 音量大
        RemoteTypeVolumeDown, // 音量下
        RemoteTypeMute, // 静音
        RemoteTypeChannelNext, // 频道上
        RemoteTypeChannelPrev, // 频道下
        RemoteTypeMenu, // menu
        RemoteTypeBack, // 返回
        RemoteTypeOK, // ok
        RemoteTypeArrowUp, // 上下左右箭头
        RemoteTypeArrowDown,
        RemoteTypeArrowLeft,
        RemoteTypeArrowRight,
        RemoteTypeNumber1,
        RemoteTypeNumber2,
        RemoteTypeNumber3,
        RemoteTypeNumber4,
        RemoteTypeNumber5,
        RemoteTypeNumber6,
        RemoteTypeNumber7,
        RemoteTypeNumber8,
        RemoteTypeNumber9,
        RemoteTypeNumber0,
        RemoteTypeAVTV, // AVTV
        RemoteTypeSlash, // 斜杠/
        RemoteTypeForward, // 快进
        RemoteTypeReverse, // 倒退
        RemoteTypeHome, // 主页
        RemoteTypeSubtitles, // 字幕
        RemoteTypeSpeech, // 语音
        RemoteTypeDischarger, // 出仓
        RemoteTypeSource, // 视源
        RemoteTypeNext, // 上一首
        RemoteTypePrev, // 下一首
        RemoteTypeLanguage, // 语言
        RemoteTypeShow // 显示
    };

    public static String getRemoteType(RemoteType type) {
        String imageName = "";
        switch (type) {
            case RemoteTypeShutDown:
                imageName = "RemoteTypeShutDown";
                break;
            case RemoteTypeStart:
                imageName = "RemoteTypeStart";
                break;
            case RemoteTypePlay:
                imageName = "RemoteTypePlay";
                break;
            case RemoteTypePause:
                imageName = "RemoteTypePause";
                break;
            case RemoteTypeStop:
                imageName = "RemoteTypeStop";
                break;
            case RemoteTypeVolumeUp:
                imageName = "RemoteTypeVolumeUp";
                break;
            case RemoteTypeVolumeDown:
                imageName = "RemoteTypeVolumeDown";
                break;
            case RemoteTypeMute:
                imageName = "RemoteTypeMute";
                break;
            case RemoteTypeChannelNext:
                imageName = "RemoteTypeChannelNext";
                break;
            case RemoteTypeChannelPrev:
                imageName = "RemoteTypeChannelPrev";
                break;
            case RemoteTypeMenu:
                imageName = "RemoteTypeMenu";
                break;
            case RemoteTypeBack:
                imageName = "RemoteTypeBack";
                break;
            case RemoteTypeOK:
                imageName = "RemoteTypeOK";
                break;
            case RemoteTypeArrowUp:
                imageName = "RemoteTypeArrowUp";
                break;
            case RemoteTypeArrowDown:
                imageName = "RemoteTypeArrowDown";
                break;
            case RemoteTypeArrowLeft:
                imageName = "RemoteTypeArrowLeft";
                break;
            case RemoteTypeArrowRight:
                imageName = "RemoteTypeArrowRight";
                break;
            case RemoteTypeNumber1:
                imageName = "RemoteTypeNumber1";
                break;
            case RemoteTypeNumber2:
                imageName = "RemoteTypeNumber2";
                break;
            case RemoteTypeNumber3:
                imageName = "RemoteTypeNumber3";
                break;
            case RemoteTypeNumber4:
                imageName = "RemoteTypeNumber4";
                break;
            case RemoteTypeNumber5:
                imageName = "RemoteTypeNumber5";
                break;
            case RemoteTypeNumber6:
                imageName = "RemoteTypeNumber6";
                break;
            case RemoteTypeNumber7:
                imageName = "RemoteTypeNumber7";
                break;
            case RemoteTypeNumber8:
                imageName = "RemoteTypeNumber8";
                break;
            case RemoteTypeNumber9:
                imageName = "RemoteTypeNumber9";
                break;
            case RemoteTypeNumber0:
                imageName = "RemoteTypeNumber0";
                break;
            case RemoteTypeAVTV:
                imageName = "RemoteTypeAVTV";
                break;
            case RemoteTypeSlash:
                imageName = "RemoteTypeSlash";
                break;
            case RemoteTypeForward:
                imageName = "RemoteTypeForward";
                break;
            case RemoteTypeReverse:
                imageName = "RemoteTypeReverse";
                break;
            case RemoteTypeHome:
                imageName = "RemoteTypeHome";
                break;
            case RemoteTypeSubtitles:
                imageName = "RemoteTypeSubtitles";
                break;
            case RemoteTypeSpeech:
                imageName = "RemoteTypeSpeech";
                break;
            case RemoteTypeDischarger:
                imageName = "RemoteTypeDischarger";
                break;
            case RemoteTypeSource:
                imageName = "RemoteTypeSource";
                break;
            case RemoteTypeNext:
                imageName = "RemoteTypeNext";
                break;
            case RemoteTypePrev:
                imageName = "RemoteTypePrev";
                break;
            case RemoteTypeLanguage:
                imageName = "RemoteTypeLanguage";
                break;
            case RemoteTypeShow:
                imageName = "RemoteTypeShow";
                break;
            default:
                imageName = "";
                break;
        }
        return imageName;
    }
}
