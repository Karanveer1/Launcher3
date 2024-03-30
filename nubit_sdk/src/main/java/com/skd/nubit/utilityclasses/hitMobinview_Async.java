package com.skd.nubit.utilityclasses;

import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONObject;

class hitMobinview_Async extends AsyncTask<Void, Void, String> {

    String rktViewResponce;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(Void... params) {

        String getrktViewDetails = "";
        try {
            getrktViewDetails = MyUtility.hitrktViewAPI(MyApplication.ctx, MyUtility
                    .mobin_View);
        } catch (Exception e) {
            /*  e.printStackTrace();*/
        }
        if (!TextUtils.isEmpty(getrktViewDetails)) {

            JSONObject json_rktView = null;
            try {
                json_rktView = new JSONObject(getrktViewDetails);
            } catch (Exception e) {
                /* e.printStackTrace();*/
            }
            String view_status = "";
            try {
                view_status = json_rktView.getJSONObject("data").getString("view_status");
                MyApplication.app_editor.putString("view_status", view_status).apply();
            } catch (Exception e) {
                /* e.printStackTrace();*/
                MyApplication.app_editor.putString("view_status", "").apply();

            }

            String interstitial_id = "";
            try {
                interstitial_id = json_rktView.getJSONObject("data").getString
                        ("interstitial_id");
                MyApplication.app_editor.putString("interstitial_id", interstitial_id).apply();

            } catch (Exception e) {
                MyApplication.app_editor.putString("interstitial_id", "").apply();
                /* e.printStackTrace();*/
            }

            String interstitial_duration = "";
            try {
                interstitial_duration = json_rktView.getJSONObject("data").getString
                        ("interstitial_duration");
                MyApplication.app_editor.putString("interstitial_duration",
                        interstitial_duration).apply();
            } catch (Exception e) {
                /* e.printStackTrace();*/
                MyApplication.app_editor.putString("interstitial_duration",
                        "").apply();
            }

            String interstitial_model = "";
            try {
                interstitial_model = json_rktView.getJSONObject("data").getString
                        ("interstitial_model");
                MyApplication.app_editor.putString("interstitial_model",
                        interstitial_model).apply();
            } catch (Exception e) {
                /*   e.printStackTrace();*/
                MyApplication.app_editor.putString("interstitial_model",
                        "").apply();
            }



            /*The Below 1 line are for showing ads inside the Wallpaper Activity*/



            /* .............................................................................*/

            try {
                MyApplication.app_editor.putString("interstitial_id_w",
                        json_rktView.getJSONObject("data").getString
                                ("interstitial_id_w")).apply();
            } catch (Exception e) {
                e.printStackTrace();
                MyApplication.app_editor.putString("interstitial_id_w", "").apply();
            }

            /*The Below Four lines are for showing ads inside the youTube Or other Player*/

            try {
                MyApplication.app_editor.putString("admob_u_rkt",
                        json_rktView.getJSONObject("data").getString
                                ("admob_u_rkt")).apply();
            } catch (Exception e) {
                e.printStackTrace();
                MyApplication.app_editor.putString("admob_u_rkt", "").apply();
            }

            try {

                MyApplication.app_editor.putString("admob_u_type",
                        json_rktView.getJSONObject("data").getString
                                ("admob_u_type")).apply();
            } catch (Exception e) {
                e.printStackTrace();
                MyApplication.app_editor.putString("admob_u_type", "").apply();
            }


            /*............................................................ */


            /*The Below Four lines are for showing ads inside the Webview and other places like
            yuptv*/

            try {
                MyApplication.app_editor.putString("admob_other_rkt",
                        json_rktView.getJSONObject("data").getString
                                ("admob_other_rkt")).apply();
            } catch (Exception e) {
                e.printStackTrace();
                MyApplication.app_editor.putString("admob_other_rkt", "").apply();
            }

            try {

                MyApplication.app_editor.putString("admob_other_type",
                        json_rktView.getJSONObject("data").getString
                                ("admob_other_type")).apply();
            } catch (Exception e) {
                e.printStackTrace();
                MyApplication.app_editor.putString("admob_other_type", "").apply();
            }


            /*............................................................ */


            String admob_status_rkt = "";
            try {
                admob_status_rkt = json_rktView.getJSONObject("data").getString
                        ("admob_status_rkt");
                MyApplication.app_editor.putString("admob_status_rkt",
                        admob_status_rkt).apply();
            } catch (Exception e) {
                /* e.printStackTrace();*/
            }


            if (!TextUtils.isEmpty(admob_status_rkt)) {
                if (admob_status_rkt.equalsIgnoreCase("yes")) {

                    String admob_states_rkt = "";
                    try {
                        admob_states_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_states_rkt");
                        MyApplication.app_editor.putString("admob_states_rkt",
                                admob_states_rkt).apply();
                    } catch (Exception e) {
                        /*  e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_states_rkt",
                                "").apply();
                    }

                    String admob_model_rkt = "";
                    try {
                        admob_model_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_model_rkt");
                        MyApplication.app_editor.putString("admob_model_rkt",
                                admob_model_rkt).apply();
                    } catch (Exception e) {
                        /*    e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_model_rkt",
                                "").apply();
                    }
                    String admob_block_rkt = "";
                    try {
                        admob_block_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_block_rkt");
                        MyApplication.app_editor.putString("admob_block_rkt",
                                admob_block_rkt).apply();
                    } catch (Exception e) {
                        /* e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_block_rkt",
                                "").apply();
                    }


                    // If admob_type_home is null then all the Ads on home screen should be
                    // Medium_Ractangle, else as per admob_type_home

                    String admob_type_home = "";
                    try {
                        admob_type_home = json_rktView.getJSONObject("data").getString
                                ("admob_type_home");
                        MyApplication.app_editor.putString("admob_type_home",
                                admob_type_home).apply();
                    } catch (Exception e) {
                        /*  e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_type_home",
                                "").apply();
                    }


                    String admob_id1_rkt = "";
                    try {
                        admob_id1_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_id1_rkt");
                        MyApplication.app_editor.putString("admob_id1_rkt",
                                admob_id1_rkt).apply();
                    } catch (Exception e) {
                        /*  e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_id1_rkt",
                                "").apply();
                    }

                    String admob_id2_rkt = "";
                    try {
                        admob_id2_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_id2_rkt");
                        MyApplication.app_editor.putString("admob_id2_rkt",
                                admob_id2_rkt).apply();
                    } catch (Exception e) {
                        /*    e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_id2_rkt",
                                "").apply();
                    }

                    String admob_id3_rkt = "";
                    try {
                        admob_id3_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_id3_rkt");
                        MyApplication.app_editor.putString("admob_id3_rkt",
                                admob_id3_rkt).apply();
                    } catch (Exception e) {
                        /* e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_id3_rkt",
                                "").apply();
                    }

                    String admob_id4_rkt = "";
                    try {
                        admob_id4_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_id4_rkt");
                        MyApplication.app_editor.putString("admob_id4_rkt",
                                admob_id4_rkt).apply();
                    } catch (Exception e) {
                        /*  e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_id4_rkt",
                                "").apply();
                    }

                    String admob_id5_rkt = "";
                    try {
                        admob_id5_rkt = json_rktView.getJSONObject("data").getString
                                ("admob_id5_rkt");
                        MyApplication.app_editor.putString("admob_id5_rkt",
                                admob_id5_rkt).apply();
                    } catch (Exception e) {
                        /* e.printStackTrace();*/
                        MyApplication.app_editor.putString("admob_id5_rkt",
                                "").apply();
                    }


                } else {
                    MyApplication.app_editor.remove("admob_states_rkt");
                    MyApplication.app_editor.remove("admob_model_rkt");
                    MyApplication.app_editor.remove("admob_block_rkt");
                    MyApplication.app_editor.remove("admob_id1_rkt");
                    MyApplication.app_editor.remove("admob_id2_rkt");
                    MyApplication.app_editor.remove("admob_id3_rkt");
                    MyApplication.app_editor.remove("admob_id4_rkt");
                    MyApplication.app_editor.remove("admob_id5_rkt");
                    MyApplication.app_editor.remove("admob_status_rkt");
                    MyApplication.app_editor.apply();
                }


            }


            if (!TextUtils.isEmpty(view_status)) {
                if (view_status.equalsIgnoreCase("yes")) {
                    String view_url = "";
                    try {
                        view_url = json_rktView.getJSONObject("data").getString("view_url");
                    } catch (Exception e) {
                        /*e.printStackTrace();*/
                    }
                    String view_duration = "";
                    try {
                        view_duration = json_rktView.getJSONObject("data").getString
                                ("view_duration");
                    } catch (Exception e) {
                        /*  e.printStackTrace();*/
                    }
                    String view_model = "";
                    try {
                        view_model = json_rktView.getJSONObject("data").getString("view_model");
                    } catch (Exception e) {
                        /*   e.printStackTrace();*/
                    }
                    String view_states = "";
                    try {
                        view_states = json_rktView.getJSONObject("data").getString
                                ("view_states");
                    } catch (Exception e) {
                        /*e.printStackTrace();*/
                    }

                    String view_height = "";
                    try {
                        view_height = json_rktView.getJSONObject("data").getString
                                ("view_height");
                    } catch (Exception e) {
                        /*     e.printStackTrace();*/
                    }

                    String view_hide_all = "";
                    try {
                        view_hide_all = json_rktView.getJSONObject("data").getString
                                ("view_hide_all");
                        MyApplication.app_editor.putString("view_hide_all",
                                view_hide_all).apply();
                    } catch (Exception e) {
                        /*   e.printStackTrace();*/
                    }


                    MyApplication.app_editor.putString("view_status", view_status);
                    MyApplication.app_editor.putString("view_url", view_url);
                    MyApplication.app_editor.putString("view_duration", view_duration);
                    MyApplication.app_editor.putString("view_model", view_model);
                    MyApplication.app_editor.putString("view_height", view_height);
                    MyApplication.app_editor.putString("view_states", view_states);
                    MyApplication.app_editor.putString("view_hide_all", view_hide_all);
                    MyApplication.app_editor.apply();

                } else {
                    MyApplication.app_editor.remove("view_status");
                    MyApplication.app_editor.remove("view_url");
                    MyApplication.app_editor.remove("view_duration");
                    MyApplication.app_editor.remove("view_model");
                    MyApplication.app_editor.remove("view_height");
                    MyApplication.app_editor.remove("view_states");
                    MyApplication.app_editor.remove("view_hide_all");
                    MyApplication.app_editor.apply();
                }
            }


            rktViewResponce = "OK";
        } else {

            rktViewResponce = "";

        }


        return rktViewResponce;
    }

    @Override
    public void onPostExecute(String args) {


    }

}