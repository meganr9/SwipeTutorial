package com.meagain.swipetutorial;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private int picturesIdIndex;
    private int[] picturesId = {R.drawable.brownpanda, R.drawable.panda, R.drawable.redpanda};
    private ImageView imageView;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        picturesIdIndex = 0;

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
            PointF StartPT = new PointF(); // Record Start Position of 'imageView'

            @Override
            public void onSwipeLeft() {
                Log.d("demo", "onFling: ");
                centerImage();
                if (picturesIdIndex < picturesId.length - 1) {
                    picturesIdIndex++;
                } else {
                    picturesIdIndex = 0;
                }
                imageView.setImageResource(picturesId[picturesIdIndex]);
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                Log.d("demo", "onFling: ");
                centerImage();

                if (picturesIdIndex > 0) {
                    picturesIdIndex--;
                } else {
                    picturesIdIndex = picturesId.length - 1;
                }
                imageView.setImageResource(picturesId[picturesIdIndex]);
                super.onSwipeRight();
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                        imageView.setX((int) (StartPT.x + mv.x));
                        StartPT.x = imageView.getX();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        DownPT.x = event.getX();
                        StartPT = new PointF(imageView.getX(), imageView.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        centerImage();
                        break;
                    default:
                        break;
                }
                return super.onTouch(v, event);
            }
        });
    }

    public void centerImage() {
        imageView.setX(screenWidth / 2 - imageView.getWidth() / 2);

    }

}
