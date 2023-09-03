package com.tanejkbd.dvtanejk.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.adapter.GamesAdapter;
import com.tanejkbd.dvtanejk.listener.PaymentListener;
import com.tanejkbd.dvtanejk.models.GameModel;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements PaymentListener {

    GameActivity activity;
    LinearLayout promotion_height;
    ImageView promotion_1, promotion_2, promotion_3, promotion_4;
    TextView user_points_text_view, noWebsiteFound, game_count_textView, total_game;
    private GamesAdapter gameAdapter;
    private ArrayList<GameModel> gameModelArrayList = new ArrayList<>();
    private RecyclerView gameRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        activity = this;
        user_points_text_view = findViewById(R.id.user_points_text_view);
        game_count_textView = findViewById(R.id.game_count_textView);
        total_game = findViewById(R.id.total_game);
        promotion_height = findViewById(R.id.promotion_height);
        promotion_1 = findViewById(R.id.promotion_1);
        promotion_2 = findViewById(R.id.promotion_2);
        promotion_3 = findViewById(R.id.promotion_3);
        promotion_4 = findViewById(R.id.promotion_4);

        Constant.adsShowingDialog(activity);

        game_count_textView.setText(Constant.getString(activity, Constant.GAME_COUNT));
        total_game.setText(Constant.getString(activity, Constant.DAILY_GAME_COUNT));
        ViewGroup.LayoutParams params = promotion_height.getLayoutParams();
        params.height = Integer.parseInt(Constant.getString(activity, Constant.PG_BANNER_HEIGHT));
        promotion_height.setLayoutParams(params);
        promotion1();
        promotion2();
        promotion3();
        promotion4();
        noWebsiteFound = findViewById(R.id.noWebsiteFound);
        gameRv = findViewById(R.id.websiteRv);
        if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
            gameRv.setLayoutManager(new LinearLayoutManager(activity));
            String json = Constant.getString(activity, Constant.GAME_LIST);
            if (!json.equalsIgnoreCase("")) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<GameModel>>() {
                }.getType();
                gameModelArrayList = gson.fromJson(json, type);
                gameAdapter = new GamesAdapter(gameModelArrayList, activity, activity);
                gameRv.setAdapter(gameAdapter);
            }
        } else {
            Constant.showInternetErrorDialog(activity, "Please Check your Internet Connection");
        }
        setWebsiteData();
    }

    private void setWebsiteData() {
        if (gameModelArrayList.isEmpty()) {
            gameRv.setVisibility(View.GONE);
            noWebsiteFound.setVisibility(View.VISIBLE);
        } else {
            gameRv.setVisibility(View.VISIBLE);
            noWebsiteFound.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
    }


    private void promotion1() {
        String isPromotion1True = Constant.getString(activity, Constant.IS_PG1_ENABLED);
        String pg1OpenWith = Constant.getString(activity, Constant.PG1_OPEN_WITH);

        if (isPromotion1True.equalsIgnoreCase("true")) {
            Glide.with(activity)
                    .load(Constant.getString(activity, Constant.PG1_IMAGE))
                    .into(promotion_1);

            promotion_1.setOnClickListener(v -> {
                if (pg1OpenWith.equalsIgnoreCase("chrome_earning_tab")) {
                    downloadAction(Constant.getString(activity, Constant.PG1_LINK));
                } else if (pg1OpenWith.equalsIgnoreCase("external_browser")) {
                    externalAction(Constant.getString(activity, Constant.PG1_LINK));
                }
            });
        } else {
            promotion_1.setVisibility(View.GONE);
        }
    }

    private void promotion2() {
        String isPromotion2True = Constant.getString(activity, Constant.IS_PG2_ENABLED);
        String pg2OpenWith = Constant.getString(activity, Constant.PG2_OPEN_WITH);

        if (isPromotion2True.equalsIgnoreCase("true")) {
            Glide.with(activity)
                    .load(Constant.getString(activity, Constant.PG2_IMAGE))
                    .into(promotion_2);

            promotion_2.setOnClickListener(v -> {
                if (pg2OpenWith.equalsIgnoreCase("chrome_earning_tab")) {
                    downloadAction(Constant.getString(activity, Constant.PG2_LINK));
                } else if (pg2OpenWith.equalsIgnoreCase("external_browser")) {
                    externalAction(Constant.getString(activity, Constant.PG2_LINK));
                }
            });
        } else {
            promotion_2.setVisibility(View.GONE);
        }
    }

    private void promotion3() {
        String isPromotion3True = Constant.getString(activity, Constant.IS_PG3_ENABLED);
        String pg3OpenWith = Constant.getString(activity, Constant.PG3_OPEN_WITH);

        if (isPromotion3True.equalsIgnoreCase("true")) {
            Glide.with(activity)
                    .load(Constant.getString(activity, Constant.PG3_IMAGE))
                    .into(promotion_3);

            promotion_3.setOnClickListener(v -> {
                if (pg3OpenWith.equalsIgnoreCase("chrome_earning_tab")) {
                    downloadAction(Constant.getString(activity, Constant.PG3_LINK));
                } else if (pg3OpenWith.equalsIgnoreCase("external_browser")) {
                    externalAction(Constant.getString(activity, Constant.PG3_LINK));
                }
            });
        } else {
            promotion_3.setVisibility(View.GONE);
        }
    }

    private void promotion4() {
        String isPromotion4True = Constant.getString(activity, Constant.IS_PG4_ENABLED);
        String pg4OpenWith = Constant.getString(activity, Constant.PG4_OPEN_WITH);

        if (isPromotion4True.equalsIgnoreCase("true")) {
            Glide.with(activity)
                    .load(Constant.getString(activity, Constant.PG4_IMAGE))
                    .into(promotion_4);

            promotion_4.setOnClickListener(v -> {
                if (pg4OpenWith.equalsIgnoreCase("chrome_earning_tab")) {
                    downloadAction(Constant.getString(activity, Constant.PG4_LINK));
                } else if (pg4OpenWith.equalsIgnoreCase("external_browser")) {
                    externalAction(Constant.getString(activity, Constant.PG4_LINK));
                }
            });
        } else {
            promotion_4.setVisibility(View.GONE);
        }
    }


    private void downloadAction(String url) {
        Intent intent = new Intent(activity, GameLoader.class);
        intent.putExtra("GAME_PASSING", url);
        startActivity(intent);
        finish();
    }

    private void externalAction(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Failed to load.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
    }

    @Override
    public void paymentIndex(int index) {
        Intent intent = new Intent(activity,GameLoader.class);
        intent.putExtra("GAME_PASSING", gameModelArrayList.get(index).getLink());
        startActivity(intent);
        finish();
    }
}