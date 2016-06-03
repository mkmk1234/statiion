package com.kun.station.util;

/*
 * Copyright (C) 2010 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class IntentUtils {
    public static void startWebActivity(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void startEmailActivity(Context context, int toResId, int subjectResId, int bodyResId) {
        startEmailActivity(context, context.getString(toResId), context.getString(subjectResId),
                context.getString(bodyResId));
    }

    public static void startEmailActivity(Context context, String to, String subject, String body) {
        try {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");

            if (!TextUtils.isEmpty(to)) {
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            }
            if (!TextUtils.isEmpty(subject)) {
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            }
            if (!TextUtils.isEmpty(body)) {
                intent.putExtra(Intent.EXTRA_TEXT, body);
            }

            final PackageManager pm = (PackageManager) context.getPackageManager();
            if (pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() == 0) {
                intent.setType("text/plain");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Log.w("Exception encountered while looking for email intent receiver.", e.getLocalizedMessage());
            Toast.makeText(context, "您的设备还未安装邮件客户端", Toast.LENGTH_SHORT).show();
        }

    }

    public static void startSMSActivity(Context context, String smsto, String smsbody) {
        try {
            Uri uri = Uri.parse("smsto:" + smsto);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", smsbody);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(e.getLocalizedMessage());
            Toast.makeText(context, "您的设备还未安装短信客户端", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bundle toBundle(Intent intent) {
        Bundle bundle = new Bundle();
        if (intent.getExtras() != null) {
            bundle.putAll(intent.getExtras());
        }

        Uri uri = intent.getData();
        if (uri != null) {
            try {
                String params = uri.getEncodedQuery();
                if (!TextUtils.isEmpty(params)) {
                    do {

                        int urlIndex = params.indexOf("url=");
                        int askIndex = params.indexOf("?");
                        if (urlIndex == 0 && askIndex > urlIndex) {
                            bundle.putString("url", params.substring(4));
                            break;
                        }

                        if (urlIndex > 0 && askIndex > urlIndex) {
                            bundle.putString("url", params.substring(urlIndex + 4));
                            params = params.substring(0, urlIndex);
                        }
                        String[] pas = params.split("&");
                        for (String str : pas) {
                            String[] tmp = str.split("=");
                            if (tmp.length > 1) {
                                bundle.putString(tmp[0], tmp[1]);
                            } else {
                                bundle.putString(tmp[0], "");
                            }
                        }
                    } while (false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }
}
