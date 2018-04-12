package com.digipodium.khushboo.imagevision.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by digipodium on 11/02/2018.
 */

public class FroyoAlbumDirFactory extends AlbumStorageDirFactory {


    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                albumName
        );
    }
}
