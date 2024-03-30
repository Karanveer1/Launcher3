package com.skd.nubit.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import com.skd.nubit.R;
import com.skd.nubit.videoplayerstuffs.UniversalVideoView;
import com.skd.nubit.activities.YouTube_Screen;
import com.skd.nubit.database.DbHandler;
import com.skd.nubit.models.Bottom_News_Pojo;
import com.skd.nubit.models.Like_DLike_Pojo;
import com.skd.nubit.models.LikesDAO;

import com.skd.nubit.mycallbacks.IsScrollingCallBack;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Bottom_News_Adapter extends RecyclerView.Adapter<Bottom_News_Adapter.MyViewHolder>
        /*implements UniversalVideoView
        .VideoViewCallback*/ {
    private ArrayList<Bottom_News_Pojo> android;
    Context context;
    int imageheight;
    String getLanguage = "";
    ArrayList<Like_DLike_Pojo> like_dLike_ArrayList;
    String news_share_flg = "";
    String like_dislike = "";

    int currentScrollPos = 0;
    int lastScrollPos = 0;


    private int mSeekPosition;
    AudioManager mAudioManager;

    /*    String test=null;*/


    public Bottom_News_Adapter(Context context, ArrayList<Bottom_News_Pojo> android) {
        this.android = android;
        this.context = context;
        imageheight = (int) (MyUtility.getScreenWidth(context) / 1.7);

        news_share_flg = MyApplication.app_sharedPreferences.getString
                ("news_share_flg", "");
        like_dislike = MyApplication.app_sharedPreferences.getString
                ("like_dislike", "");
        getLanguage = MyApplication.app_sharedPreferences.getString
                ("language_selection", "");


        try {
            DbHandler dbHandler = DbHandler.getInstance(context);
            SQLiteDatabase _sqlDb = dbHandler.openDb(1);
            like_dLike_ArrayList = LikesDAO.getInstance().getTracksFromDB(_sqlDb);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            mAudioManager = (AudioManager) context.getSystemService(Context
                    .AUDIO_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*        new HitforLikeDislike().execute();*/

        try {
            if (like_dLike_ArrayList != null && like_dLike_ArrayList.size() != 0 &&
                    like_dLike_ArrayList.size() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                Date date_1 = null;
                Date date_2 = null;
                try {
                    date_1 = sdf.parse(MyUtility.getCurrentTime_Irctc());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    date_2 = sdf.parse((like_dLike_ArrayList.get(0).getEventDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!(date_1.equals(date_2))) {
                    new HitforLikeDislike().execute();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Bottom_News_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .bottom_news_adapter_items, viewGroup, false);
        Bottom_News_Adapter.MyViewHolder mViewHold = new Bottom_News_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Bottom_News_Adapter.MyViewHolder viewHolder, int i) {

        final Bottom_News_Pojo dataItem = android.get(i);
        final Bottom_News_Adapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getImage()).thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.placeholder_you_tube)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_you_tube)
                .into(myViewHolder.img_bottomNews);

        myViewHolder.txt_bottomNews_title.setText(dataItem.getTitle());
        myViewHolder.txt_bottomNews_des.setText(dataItem.getDescription());

        if (!TextUtils.isEmpty(dataItem.getPosted_date()) && !TextUtils.isEmpty(dataItem
                .getPosted_time())) {
            myViewHolder.newsDate.setText(dataItem.getPosted_date() + " , " + dataItem
                    .getPosted_time());
        }

        if (!TextUtils.isEmpty(dataItem.getPosted_date()) && TextUtils.isEmpty(dataItem
                .getPosted_time())) {
            myViewHolder.newsDate.setText(dataItem.getPosted_date());
        }

        if (TextUtils.isEmpty(dataItem.getPosted_date()) && !TextUtils.isEmpty(dataItem
                .getPosted_time())) {
            myViewHolder.newsDate.setText(dataItem
                    .getPosted_time());
        }


        String fullStory = MyApplication.app_sharedPreferences.getString
                ("readFullStory_forTitle", "");


        if (!TextUtils.isEmpty(fullStory)) {
            myViewHolder.btn_bottomNews_readMore.setText(fullStory);
        }

        if (!TextUtils.isEmpty(dataItem.getNewsBy())) {
            myViewHolder.newsBy.setVisibility(View.VISIBLE);
            myViewHolder.newsBy.setText(dataItem.getNewsBy());
        }
        if (TextUtils.isEmpty(dataItem.getNewsBy())) {
            myViewHolder.newsBy.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(dataItem.getNews_type()) && (dataItem
                .getNews_type().equalsIgnoreCase("video") || dataItem
                .getNews_type().equalsIgnoreCase("youtube"))) {
            myViewHolder.btn_bottomNews_readMore.setVisibility(View.GONE);
            myViewHolder.center_play_btn_uTube.setVisibility(View.VISIBLE);
            myViewHolder.center_play_btn_uTube.setImageResource(R.drawable.icon_play);
        } else {
            myViewHolder.center_play_btn_uTube.setVisibility(View.GONE);
            myViewHolder.btn_bottomNews_readMore.setVisibility(View.VISIBLE);
        }

        myViewHolder.center_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewHolder.mVideoView.isPlaying()) {
                    myViewHolder.mVideoView.pause();
                    myViewHolder.center_play_btn.setImageResource(R.drawable.icon_play);
                    try {
                        MyUtility.saveTracksInDB(context, dataItem.getRedirect_link(),
                                dataItem.getRedirect_link(), "Bottom News Video", "0", dataItem
                                        .getTitle
                                                (), String.valueOf(((myViewHolder.mVideoView
                                        .getCurrentPosition()) / 1000)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myViewHolder.center_play_btn.setVisibility(View.VISIBLE);
                        }
                    }, 2000);

                } else {

                    MyUtility.UnmuteDeviceAudio(mAudioManager);
                    myViewHolder.mVideoView.start();
                    myViewHolder.center_play_btn.setImageResource(R.drawable.icon_pause);
                    /* myViewHolder.center_play_btn.setVisibility(View.GONE);*/
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myViewHolder.center_play_btn.setVisibility(View.GONE);
                        }
                    }, 1000);

                    lastScrollPos = currentScrollPos;
                    PagersAutoScrollControllerCallback.getInstance().notify(false,
                            "bottom_news_play",
                            0);

                }


            }
        });

        myViewHolder.mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (myViewHolder.mVideoView.isPlaying()) {
                    myViewHolder.mVideoView.pause();
                    myViewHolder.center_play_btn.setImageResource(R.drawable.icon_play);
                    try {
                        MyUtility.saveTracksInDB(context, dataItem.getRedirect_link(),
                                dataItem.getRedirect_link(), "Bottom News Video", "0", dataItem
                                        .getTitle
                                                (), String.valueOf(((myViewHolder.mVideoView
                                        .getCurrentPosition()) / 1000)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myViewHolder.center_play_btn.setVisibility(View.VISIBLE);
                        }
                    }, 2000);

                } else {

                    MyUtility.UnmuteDeviceAudio(mAudioManager);
                    myViewHolder.mVideoView.start();
                    myViewHolder.center_play_btn.setImageResource(R.drawable.icon_pause);
                    /* myViewHolder.center_play_btn.setVisibility(View.GONE);*/
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myViewHolder.center_play_btn.setVisibility(View.GONE);
                        }
                    }, 1000);

                    lastScrollPos = currentScrollPos;
                    PagersAutoScrollControllerCallback.getInstance().notify(false,
                            "bottom_news_play",
                            0);

                }


                return false;
            }
        });

        myViewHolder.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                myViewHolder.center_play_btn.setVisibility(View.VISIBLE);
                myViewHolder.center_play_btn.setImageResource(R.drawable.icon_play);
                try {
                    MyUtility.saveTracksInDB(context, dataItem.getRedirect_link(),
                            dataItem.getRedirect_link(), "Bottom News Video", "0", dataItem.getTitle
                                    (), String.valueOf(((myViewHolder.mVideoView
                                    .getCurrentPosition()) / 1000)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        /* Here is video container insertion logic start, if getNews_type=video*/

        if (!TextUtils.isEmpty(dataItem.getNews_type()) && dataItem.getNews_type()
                .equalsIgnoreCase("video")) {
            myViewHolder.img_bottomNews.setVisibility(View.GONE);
            myViewHolder.mp4_layout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(dataItem.getRedirect_link())) {

               /* myViewHolder.media_controller.hideScaleButtion();
                myViewHolder.media_controller.show_img_audio_controller();
                myViewHolder.media_controller.muteAudio();*/

                if (mSeekPosition > 0) {
                    myViewHolder.mVideoView.seekTo(mSeekPosition);
                }
                myViewHolder.mp4_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        int width = myViewHolder.mp4_layout.getWidth();
                        /*cachedHeight = (int) (width * 405f / 720f);*/
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                        ViewGroup.LayoutParams videoLayoutParams = myViewHolder.mp4_layout
                                .getLayoutParams();
                        videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        videoLayoutParams.height = imageheight;
                        myViewHolder.mp4_layout.setLayoutParams(videoLayoutParams);


                        myViewHolder.mVideoView.setVideoPath(dataItem.getRedirect_link());
                        myViewHolder.mVideoView.seekTo(1000);

                        myViewHolder.mVideoView.requestFocus();

                        myViewHolder.mVideoView.start();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                myViewHolder.mVideoView.pause();
                                myViewHolder.center_play_btn.setImageResource(R.drawable
                                        .icon_play);
                                /*         myViewHolder.center_play_btn.setVisibility(View
                                .VISIBLE);*/


                            }
                        }, 2000);

                    }
                });


            }
        } else {
            myViewHolder.img_bottomNews.setVisibility(View.VISIBLE);
            myViewHolder.mp4_layout.setVisibility(View.GONE);
        }


        /* Here is video container insertion logic end*/


        try {

            // Log.d("rkt_likes",String.valueOf(like_dLike_ArrayList.size()));
            if (like_dLike_ArrayList.size() != 0 & like_dLike_ArrayList.size() > 0) {
                for (int j = 0; j < like_dLike_ArrayList.size(); j++) {
                    if (like_dLike_ArrayList.get(j).getRedirectURL().equalsIgnoreCase(dataItem
                            .getRedirect_link())) {
                        if ((like_dLike_ArrayList.get(j).getEventType()).equalsIgnoreCase
                                ("Like")) {
                            myViewHolder.img_bottomNews_like.setImageResource(R.drawable
                                    .like_selected);
                        } else if ((like_dLike_ArrayList.get(j).getEventType()).equalsIgnoreCase
                                ("DisLike")) {
                            myViewHolder.img_bottomNews_dislike.setImageResource(R.drawable
                                    .dislike_selected);
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        myViewHolder.btn_bottomNews_readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (MyUtility.isConnectedToInternet()) {
                    MyApplication.getInstance().trackEvent("Bottom News", dataItem
                            .getRedirect_link(), dataItem.getTitle());
                    MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
                                    .getRedirect_link(), dataItem.getImage(),
                            "Bottom News", dataItem.getOpen_with(), dataItem.getTitle());
                } else {
                    MyUtility.NoInternet_Msg(context);
                }


            }
        });
        myViewHolder.img_bottomNews_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyApplication.getInstance().trackEvent("News Share", dataItem
                            .getRedirect_link(), dataItem.getTitle());
                    MyUtility.My_share(context, dataItem.getRedirect_link() + " \n\n" + dataItem
                            .getTitle(), "news");
                } catch (Exception e) {
                    Toast.makeText(context, "Opps, Something went wrong, Pls try later", Toast
                            .LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


        myViewHolder.bottomNews_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyApplication.getInstance().trackEvent("Bottom News", dataItem.getRedirect_link()
                        , dataItem.getTitle());


                if (MyUtility.isConnectedToInternet()) {
                    if (!TextUtils.isEmpty(dataItem.getNews_type()) && dataItem.getNews_type()
                            .equalsIgnoreCase("video")) {

                    } else if (!TextUtils.isEmpty(dataItem.getNews_type()) && dataItem
                            .getNews_type()
                            .equalsIgnoreCase("youtube")) {

                        MyUtility.saveTracksInDB(context, dataItem.getRedirect_link(),
                                dataItem.getRedirect_link(), "Bottom News Video", "0", dataItem
                                        .getTitle
                                                (), "");

                        Intent send = new Intent(context, YouTube_Screen.class);
                        send.putExtra("videoUrl", dataItem.getRedirect_link());
                        send.putExtra("get_YouTubeID", dataItem.getYoutube_id());
                        send.putExtra("videoTitle", dataItem.getTitle());
                        context.startActivity(send);

                    } else {
                        MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
                                        .getRedirect_link(), dataItem.getImage(),
                                "Bottom News", dataItem.getOpen_with(), dataItem.getTitle());
                    }


                } else {
                    MyUtility.NoInternet_Msg(context);
                }
            }
        });

        myViewHolder.img_bottomNews_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myViewHolder.img_bottomNews_like.getDrawable().getConstantState() == context
                        .getResources().getDrawable(R.drawable.like_selected).getConstantState()) {
                    myViewHolder.img_bottomNews_like.setImageResource(R.drawable.like_desele);
                } else {
                    myViewHolder.img_bottomNews_like.setImageResource(R.drawable.like_selected);
                }

                MyUtility.saveLikesInDB(context, "Like", dataItem.getRedirect_link(),
                        dataItem.getNewsBy(), getLanguage, MyUtility.getCurrentTime_Irctc(),
                        dataItem.getTitle());
                MyApplication.getInstance().trackEvent("Like", dataItem
                        .getRedirect_link(), dataItem.getTitle());

                myViewHolder.img_bottomNews_dislike.setImageResource(R.drawable.dislike_desele);

            }
        });

        myViewHolder.img_bottomNews_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myViewHolder.img_bottomNews_dislike.getDrawable().getConstantState() == context
                        .getResources().getDrawable(R.drawable.dislike_selected).getConstantState
                                ()) {

                    myViewHolder.img_bottomNews_dislike.setImageResource(R.drawable.dislike_desele);
                } else {
                    myViewHolder.img_bottomNews_dislike.setImageResource(R.drawable
                            .dislike_selected);
                }

                MyUtility.saveLikesInDB(context, "DisLike", dataItem.getRedirect_link(),
                        dataItem.getNewsBy(), getLanguage, MyUtility.getCurrentTime_Irctc(),
                        dataItem.getTitle());

                MyApplication.getInstance().trackEvent("DisLike", dataItem
                        .getRedirect_link(), dataItem.getTitle());
                myViewHolder.img_bottomNews_like.setImageResource(R.drawable.like_desele);


            }
        });
    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements IsScrollingCallBack
            .interfaceScrollUpdate {

        private ImageView img_bottomNews, img_bottomNews_share, img_bottomNews_like,
                img_bottomNews_dislike, center_play_btn_uTube;

        FrameLayout mp4_layout;
        ImageView center_play_btn;
        UniversalVideoView mVideoView;

        Button btn_bottomNews_readMore;
        FrameLayout admob_blank_layout;

        private TextView txt_bottomNews_title, txt_bottomNews_des, newsBy, newsDate;

        CardView bottomNews_cardview;


        public MyViewHolder(View view) {
            super(view);
            img_bottomNews = view.findViewById(R.id.img_bottomNews);
            img_bottomNews_like = view.findViewById(R.id.img_bottomNews_like);
            img_bottomNews_dislike = view.findViewById(R.id.img_bottomNews_dislike);
            img_bottomNews_share = view.findViewById(R.id.img_bottomNews_share);
            txt_bottomNews_title = view.findViewById(R.id.txt_bottomNews_title);
            txt_bottomNews_des = view.findViewById(R.id.txt_bottomNews_des);
            btn_bottomNews_readMore = view.findViewById(R.id.btn_bottomNews_readMore);
            bottomNews_cardview = view.findViewById(R.id.bottomNews_cardview);
            center_play_btn_uTube = view.findViewById(R.id.center_play_btn_uTube);


            center_play_btn = view.findViewById(R.id.center_play_btn);
            mp4_layout = view.findViewById(R.id.mp4_layout);
            mVideoView = view.findViewById(R.id.videoView);

            try {
                IsScrollingCallBack.getInstance().setListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }


            newsBy = view.findViewById(R.id.newsBy);
            newsDate = view.findViewById(R.id.newsDate);
            //  img_bottomNews.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup
            // .LayoutParams.MATCH_PARENT, MyUtility.dpToPx(260)));

            img_bottomNews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, imageheight));
            img_bottomNews.setScaleType(ImageView.ScaleType.FIT_XY);


            if (!TextUtils.isEmpty(news_share_flg) & news_share_flg.equalsIgnoreCase
                    ("1")) {
                img_bottomNews_share.setVisibility(View.VISIBLE);
            } else {
                img_bottomNews_share.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(like_dislike) & like_dislike.equalsIgnoreCase
                    ("1")) {
                img_bottomNews_like.setVisibility(View.VISIBLE);
                img_bottomNews_dislike.setVisibility(View.VISIBLE);
            } else {
                img_bottomNews_like.setVisibility(View.GONE);
                img_bottomNews_dislike.setVisibility(View.GONE);
            }


        }


        @Override
        public void update_onScrollStart(boolean z, String callFrom, int position) {
            currentScrollPos = position;

            try {
                if (lastScrollPos != 0 && (((currentScrollPos - lastScrollPos) > 700) || (
                        (lastScrollPos - currentScrollPos) > 700))) {

                    lastScrollPos = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyUtility.muteDeviceAudio(mAudioManager);
                        }
                    }, 1000);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HitforLikeDislike extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(Void... params) {


            JSONObject save_json_jsonobject = null;

            String getUploadResponse = "";
            JsonArray likeDisArray = null;
            try {
                if (like_dLike_ArrayList.size() != 0 & like_dLike_ArrayList.size() > 0) {
                    Gson gson = new GsonBuilder().create();
                    likeDisArray = gson.toJsonTree(like_dLike_ArrayList).getAsJsonArray();
                } else {
                    likeDisArray = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            getUploadResponse = MyUtility.hitLikeDisAPI(context, MyUtility.uploadLikesDisAPI,
                    likeDisArray);
            if (!TextUtils.isEmpty(getUploadResponse)) {
                try {
                    save_json_jsonobject = new JSONObject(getUploadResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if ((save_json_jsonobject.getString("response")).equalsIgnoreCase("success")) {
                        getUploadResponse = "success";
                    } else {
                        getUploadResponse = "error";
                    }

                } catch (Exception e) {
                    getUploadResponse = "error";
                    e.printStackTrace();
                }


            } else {
                getUploadResponse = "error";
            }


            return getUploadResponse;
        }

        @Override
        public void onPostExecute(String args) {


            if (args.equalsIgnoreCase("success")) {
                try {
                    DbHandler dbHandler = DbHandler.getInstance(context);
                    SQLiteDatabase _sqlDb = dbHandler.openDb(1);
                    LikesDAO.getInstance().deleteAllData(_sqlDb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}