package com.afeastoffriends.doctorsaathi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    //aba hami variables harulai globally declare garum
    int val;
    String PP, PD, DP, DD, DT, DC, TP, TD, TT, TC, TPA, TCH,CP,CD,CT,CC,CPA,CCH,CS,CA,PAP,PAD,PAT,PAC,PAPA,PACH,PAS,PAAT,PANA,PADA;

    @Override
    public void onReceive(Context context, Intent intent) {
        //lets fetch the data from the preceding activity
        String taken = intent.getExtras().getString("stat");
        int val = intent.getExtras().getInt("Kati");
        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        service_intent.putExtra("Status", taken);
        service_intent.putExtra("key", val);
        if (val == 11) {
            PP = intent.getExtras().getString("FF");
            PD = intent.getExtras().getString("FS");
            service_intent.putExtra("first_first", PP);
            service_intent.putExtra("first_second", PD);
        } else if (val == 21) {
            DP = intent.getExtras().getString("SF");
            DD = intent.getExtras().getString("SS");
            service_intent.putExtra("second_first", DP);
            service_intent.putExtra("second_second", DD);
        } else if (val == 22) {
            DT = intent.getExtras().getString("ST");
            DC = intent.getExtras().getString("SF");
            service_intent.putExtra("second_third", DT);
            service_intent.putExtra("second_fourth", DC);
        } else if (val == 31) {
            TP = intent.getExtras().getString("TF");
            TD = intent.getExtras().getString("TS");
            service_intent.putExtra("third_first", TP);
            service_intent.putExtra("third_second", TD);
        }
        else if (val == 32) {
            TT = intent.getExtras().getString("TT");
            TC = intent.getExtras().getString("TF");
            service_intent.putExtra("third_third", TT);
            service_intent.putExtra("third_fourth", TC);
        }
        else if (val == 33) {
            TPA = intent.getExtras().getString("TFI");
            TCH = intent.getExtras().getString("TSE");
            service_intent.putExtra("third_fifth", TPA);
            service_intent.putExtra("third_sixth", TCH);
        }
        else if (val == 41) {
            CP = intent.getExtras().getString("FF");
            CD = intent.getExtras().getString("FS");
            service_intent.putExtra("fourth_first", CP);
            service_intent.putExtra("fourth_second", CD);
        }
        else if (val == 42) {
            CT = intent.getExtras().getString("FT");
            CC = intent.getExtras().getString("FFO");
            service_intent.putExtra("fourth_third", CT);
            service_intent.putExtra("fourth_fourth", CC);
        }
        else if (val == 43) {
            CPA = intent.getExtras().getString("FFI");
            CCH = intent.getExtras().getString("FSI");
            service_intent.putExtra("fourth_fifth", CPA);
            service_intent.putExtra("fourth_sixth", CCH);
        }
        else if (val == 44) {
            CS = intent.getExtras().getString("FSE");
            CA = intent.getExtras().getString("FE");
            service_intent.putExtra("fourth_seventh", CS);
            service_intent.putExtra("fourth_eightd", CA);
        }
        else if (val == 51) {
            PAP = intent.getExtras().getString("FFI");
            PAD = intent.getExtras().getString("FS");
            service_intent.putExtra("fifth_first", PAP);
            service_intent.putExtra("fifth_second", PAD);
        }
        else if (val == 52) {
            PAT = intent.getExtras().getString("FT");
            PAC = intent.getExtras().getString("FFO");
            service_intent.putExtra("fifth_third", PAT);
            service_intent.putExtra("fifth_fourth", PAC);
        }
        else if (val == 53) {
            PAPA = intent.getExtras().getString("FFI");
            PACH = intent.getExtras().getString("FSI");
            service_intent.putExtra("fifth_fifth", PAPA);
            service_intent.putExtra("fifth_sixth", PACH);
        }
        else if (val == 54) {
            PAS = intent.getExtras().getString("FSE");
            PAAT = intent.getExtras().getString("FE");
            service_intent.putExtra("fifth_seventh", PAS);
            service_intent.putExtra("fifth_eightd", PAAT);
        }
        else if (val == 55) {
            PANA = intent.getExtras().getString("FN");
            PADA = intent.getExtras().getString("FTE");
            service_intent.putExtra("fifth_ninth", PANA);
            service_intent.putExtra("fifht_tenth", PADA);
        }
        String namaste = String.valueOf(val);
        Log.e("aayeko state : ", taken);
        Log.e("Aayeko katiota :", namaste);
        context.startService(service_intent);
    }
}
