package com.digipodium.khushboo.imagevision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digipodium.khushboo.imagevision.R;
import com.digipodium.khushboo.imagevision.utils.PackageManagerUtils;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ObjectDetection extends AppCompatActivity {
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private TextView objectresult;
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private String mCurrentPhotoPath;
    private ImageView selectedImage;
    private final String LOG_TAG = "Object_Detection";
    private Uri iImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detection);
        objectresult = (TextView) findViewById(R.id.tvobjectresult);
        selectedImage = findViewById(R.id.imviewobject);
        ;

        Intent i = getIntent();
        if (i.hasExtra("Imagepath")) {
            String imagepath = i.getStringExtra("Imagepath");

            Glide.with(this).load(imagepath).into(selectedImage);
            iImageUri = Uri.parse(imagepath);
            uploadImage(iImageUri);
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            mCurrentPhotoPath = uri.toString();
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap bitmap = resizeBitmap(b);
                callCloudVision(bitmap);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        } else {
            Log.e(LOG_TAG, "Null image was returned.");
        }
    }

    public Bitmap resizeBitmap(Bitmap bitmap) {
        int maxDimension = 2048;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);

    }


    private void callCloudVision(final Bitmap bitmap) throws IOException {
        // TODO: 10-10-2017 Add a progress dialog
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(getString(R.string.cloud_key)) {
                        @Override
                        protected void initializeVisionRequest(VisionRequest<?> request) throws IOException {
                            super.initializeVisionRequest(request);
                            String packageName = getPackageName();
                            request.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);
                            String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);
                            request.
                                    getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                        }
                    };
                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);
                    Vision vision = builder.build();
                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {
                        {
                            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                            // Add the image
                            Image base64EncodedImage = new Image();
                            // Convert the bitmap to a JPEG
                            // Just in case it's a format that Android understands but Cloud Vision
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            byte[] imageBytes = byteArrayOutputStream.toByteArray();

                            // Base64 encode the JPEG
                            base64EncodedImage.encodeContent(imageBytes);
                            annotateImageRequest.setImage(base64EncodedImage);

                            // add the features we want
                            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                                Feature label_detection = new Feature();
                                label_detection.setType("LABEL_DETECTION");
                                label_detection.setMaxResults(15);
                                add(label_detection);
                            }});
                            add(annotateImageRequest);
                        }
                    });
                    Vision.Images.Annotate annotateRequest;
                    annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return response;
                } catch (GoogleJsonResponseException e) {
                    return "failed to make request because" + e.getContent();
                } catch (IOException e) {
                    return "failed to make request because of other IOException" + e.getMessage();
                }
            }

            protected void onPostExecute(Object result) {
                if (result != null) {
                   /* analysisView.setValue((BatchAnnotateImagesResponse) result);
                    Log.d("haha", "process finished");
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }*/
                    try {
                        String data = convertResponseToString((BatchAnnotateImagesResponse) result);
                        objectresult.setText(data);
                    } catch (Exception e) {
                        Toast.makeText(ObjectDetection.this, "e", Toast.LENGTH_SHORT).show();
                        objectresult.setText((String) result);
                    }
                }
            }
        }.execute();
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("Results:\n\n");
        message.append("Labels found :\n\n");
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message.append(" "+label.getDescription());
                message.append("\n");
            }
        } else {
            message.append("nothing\n");
        }


        return message.toString();
    }

}
