package cordova.plugin.fingerprintauth;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import android.content.Context;
import android.util.Log;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class FingerPrintAuth extends CordovaPlugin implements FingerprintUtil.Callback {
    private static final String TAG = FingerPrintAuth.class.getSimpleName();    

    //private Context mContext;
    private FingerprintUtil mFingerPrintUtil = null;
    public static CallbackContext mCallbackContext;
    private static final int PERMISSIONS_REQUEST_FINGERPRINT = 55433;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "FingerPrintAuth init");
        Context context = cordova.getActivity().getApplicationContext();

        if (Build.VERSION.SDK_INT < 23) {
            return;
        }

		mFingerPrintUtil = new FingerprintUtil(context, this);
    }
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        mCallbackContext = callbackContext;
        boolean result = false;

        if (mFingerPrintUtil == null || Build.VERSION.SDK_INT < 23) {
            this.notSupportFingerPrint();
            return true;
        }

        if (action.equals("isFingerPrintAvailable")) {
            this.isFingerPrintAvailable();
            result = true;
        } else if (action.equals("startAuth")) {
            this.startAuth();
            result = true;
        } else if (action.equals("stopAuth")) {
            this.stopAuth();
            result = true;
        }
        return result;
    }

	@Override
	public void onAuthenticated() {
        Log.d(TAG, "onAuthenticated");
		mCallbackContext.success("0");        
	}

	@Override
	public void onError(int errMsgId, CharSequence errMsg) {
        Log.d(TAG, "onError : " + errMsg);
        mCallbackContext.error("-2");
	}

	@Override
	public void onFailed() {
        Log.d(TAG, "onFailed");        
        mCallbackContext.error("-1");
	}        

    private void isFingerPrintAvailable() {
        if (mFingerPrintUtil.isFingerprintAuthAvailable()) {
            mCallbackContext.success("0");
        } else {
            mCallbackContext.error("-1");
        }
    }

    private void startAuth() {
        mFingerPrintUtil.startListening();
    }

    private void stopAuth() {
        mFingerPrintUtil.stopListening();
        mCallbackContext.success("0");
    }

    private void notSupportFingerPrint() {
        mCallbackContext.error("-2");
    }
}
