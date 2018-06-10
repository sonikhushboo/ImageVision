package com.digipodium.khushboo.imagevision;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;
import com.stephentuso.welcome.WelcomeHelper;

/**
 * Created by This Pc on 15-11-2017.
 */

//visible only once
public class IntroActivity extends WelcomeActivity {

    WelcomeHelper welcomeScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected WelcomeConfiguration configuration() {
        WelcomeConfiguration configuration = new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(new TitlePage(R.drawable.first,
                        "IMAGE VISION")
                        .background(R.color.DarkRed)
                )
                .page(new BasicPage(R.drawable.third,
                        "TEXT RECOGNITION",

                        "Text recognition uses a OCR(Optical Character Recognition)" +
                                "\n" +
                                "It is the mechanical orelectronic conversion of images of typed,handwritten or printed text into machine-encoded text,whether from scanned document,a photo of a document,a scene-photo or from subtitle text superimposed on an image")

                        .background(R.color.DarkRed)
                )
                .page(new BasicPage(R.drawable.second,
                        "BAR CODE DETCTION",
                        "It is scanner")
                        .background(R.color.DarkRed)

                )
                .swipeToDismiss(true)
                .backButtonSkips(false)
                .canSkip(true)
                .animateButtons(true)
                .build();
        return configuration;
    }

}



