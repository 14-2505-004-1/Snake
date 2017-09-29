package net.npaka.obakecamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.view.Gravity.CENTER;

//おばけビュー
public class ObakeView extends RelativeLayout implements
    View.OnClickListener  {
    private final static int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private Activity activity;
    private CameraView cameraView;
    private ImageView Zetsu;

    //コンストラクタ
    public ObakeView(Activity parent) {
        super(parent);
        activity = parent;
        RelativeLayout.LayoutParams params;
        Bitmap bmp;

        //カメラビュー
        cameraView = new CameraView(activity);
        params = new RelativeLayout.LayoutParams(MP, MP);
        params.setMargins(0, 0, 0, (int)Util.dp2px(120));
        cameraView.setLayoutParams(params);
        addView(cameraView);



        Zetsu = new ImageView(activity);
        Zetsu.setImageResource(R.drawable.zetsu000);
        params.addRule(CENTER);
        addView(Zetsu);

        //フッタ
        RelativeLayout footer = new RelativeLayout(activity);
        params = new RelativeLayout.LayoutParams(MP, Util.dp2px(120));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        footer.setBackgroundColor(Color.BLACK);
        footer.setLayoutParams(params);
        addView(footer);

        //シャッターボタン
        params = new RelativeLayout.LayoutParams(
            Util.dp2px(100), Util.dp2px(100));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageButton btnShot = makeImageButton(
            R.drawable.s_resource_camera_btn_shutter, params, "shutter");
        footer.addView(btnShot);

        //フリップボタン
        params = new RelativeLayout.LayoutParams(
            Util.dp2px(100), Util.dp2px(100));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(Util.dp2px(20), 0, 0, 0);
        ImageButton btnFlip = makeImageButton(
            R.drawable.btn_flip, params, "flip");
        footer.addView(btnFlip);

        //写真ボタン
        params = new RelativeLayout.LayoutParams(
            Util.dp2px(100), Util.dp2px(100));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(0, 0, Util.dp2px(20), 0);
        ImageButton btnPhoto = makeImageButton(
            R.drawable.s_resource_camera_btn_frame, params, "photo");
        footer.addView(btnPhoto);


    }

    //イメージボタンの生成
    private ImageButton makeImageButton(int resId,
        RelativeLayout.LayoutParams params, String tag) {
        ImageButton button = new ImageButton(activity);
        button.setTag(tag);
        button.setImageResource(resId);
        button.setLayoutParams(params);
        button.setOnClickListener(this);
        button.setBackground(null);
        return button;
    }

    //ボタン押下時に呼ばれる
    public void onClick(View view) {
        String tag = (String)view.getTag();
        //撮影
        if (tag.equals("shutter")) {
            cameraView.takePicture();
        }
        //フリップ
        else if (tag.equals("flip")) {
            cameraView.flip();
        }
        //ギャラリー起動
        else if (tag.equals("photo")) {
            try {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_VIEW);
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}