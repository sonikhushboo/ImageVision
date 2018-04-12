package com.digipodium.khushboo.imagevision;

import android.accounts.Account;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by digipodium on 20/2/18.
 */

class GetTokenTask extends AsyncTask<Void, Void, Void> {

    int mRequestCode;
    Activity mActivity;
    Account mAccount;
    String mScope;

    GetTokenTask(AnalysisActivity analysis_activity, Account mAccount, String scope, int requestAccountAuthorization) {
        this.mActivity = analysis_activity;
        this.mScope = scope;
        this.mAccount = mAccount;
        this.mRequestCode = requestAccountAuthorization;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String token = fetchToken();
            if (token != null) {
                ((AnalysisActivity)mActivity).onTokenReceived(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String fetchToken() throws IOException {
        String accessToken;
        try {
            accessToken = GoogleAuthUtil.getToken(mActivity, mAccount, mScope);
            GoogleAuthUtil.clearToken (mActivity, accessToken); // used to remove stale tokens.
            accessToken = GoogleAuthUtil.getToken(mActivity, mAccount, mScope);
            return accessToken;
        } catch (UserRecoverableAuthException userRecoverableException) {
            mActivity.startActivityForResult(userRecoverableException.getIntent(), mRequestCode);
        } catch (GoogleAuthException fatalException) {
            fatalException.printStackTrace();
        }
        return null;
    }
}
