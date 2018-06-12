package cordova.plugin.fingerprintauth;

import android.util.Log;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

public class FingerprintUtil extends FingerprintManager.AuthenticationCallback {
    private static final String TAG = FingerprintUtil.class.getSimpleName();

    private final FingerprintManager mFingerprintManager;
    private final Callback mCallback;
    private CancellationSignal mCancellationSignal;

    private boolean mSelfCancelled;


    public FingerprintUtil(Context context, Callback callback) {
        mFingerprintManager = context.getSystemService(FingerprintManager.class);
        mCallback = callback;
    }

    public boolean isFingerprintAuthAvailable() {
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        if(mFingerprintManager == null) {
            return false;
        }

        return mFingerprintManager.isHardwareDetected()
                && mFingerprintManager.hasEnrolledFingerprints();
    }

    public void startListening() {
        if (!isFingerprintAuthAvailable()) {
            Log.d(TAG, "disable");
            return;
        }

        mCancellationSignal = new CancellationSignal();
        mSelfCancelled = false;
        mFingerprintManager
                .authenticate(null, mCancellationSignal, 0 /* flags */, this, null);
        Log.d(TAG, "Fingerprint start");
    }

    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            Log.d(TAG, "Fingerprint stop");
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Log.d(TAG, "[Fingerprint Error] id = " + errMsgId + "msg = " + errString);
        if (!mSelfCancelled) {
            mCallback.onError(errMsgId, errString);
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Log.d(TAG, "[Fingerprint Help] id = " + helpMsgId + "msg = " + helpString);
        if (!mSelfCancelled) {
            mCallback.onError(helpMsgId, helpString);
        }
    }

    @Override
    public void onAuthenticationFailed() {
        Log.d(TAG, "[Fingerprint Failed]");
        if (!mSelfCancelled) {
            mCallback.onFailed();
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Log.d(TAG, "[Fingerprint Succeeded]");
        mCallback.onAuthenticated();
    }

    public interface Callback {

        void onAuthenticated();

        void onError(int errMsgId, CharSequence errMsg);

        void onFailed();
    }
}
