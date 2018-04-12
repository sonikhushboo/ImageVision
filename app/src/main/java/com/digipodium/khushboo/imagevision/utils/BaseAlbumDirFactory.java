package com.digipodium.khushboo.imagevision.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by digipodium on 11/02/2018.
 */

public class BaseAlbumDirFactory extends AlbumStorageDirFactory{


    private static final String CAMERA_DIR = "ObjectDetection";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }
}
