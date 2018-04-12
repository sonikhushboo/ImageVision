package com.digipodium.khushboo.imagevision.utils;

import java.io.File;

/**
 * Created by digipodium on 11/02/2018.
 */

public abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
