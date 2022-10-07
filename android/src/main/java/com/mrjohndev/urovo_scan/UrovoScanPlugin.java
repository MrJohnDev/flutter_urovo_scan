package com.mrjohndev.urovo_scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;

/**
 * UrovoScanPlugin
 */
public class UrovoScanPlugin implements FlutterPlugin, EventChannel.StreamHandler {

    private Context applicationContext;

    private BroadcastReceiver receiver;
    private EventChannel eventChannel;

    private static final String CHANNEL = "com.mrjohndev.urovo_scan/plugin";
    private static final String ACTION_DECODE = ScanManager.ACTION_DECODE;
    private static final String BARCODE_STRING_TAG = ScanManager.BARCODE_STRING_TAG;
    private static final String BARCODE_TYPE_TAG = ScanManager.BARCODE_TYPE_TAG;
    private static final String BARCODE_LENGTH_TAG = ScanManager.BARCODE_LENGTH_TAG;
    private static final String DECODE_DATA_TAG = ScanManager.DECODE_DATA_TAG;

    private final static String PARAM_BC_VAL = "barcode_string";
    private final static String PARAM_BC_TYPE = "barcode_type";

    private ScanManager mScanManager = null;


    private BroadcastReceiver onScanReceiver(final EventChannel.EventSink events) {
        Log.i("UrovoScanPlugin", "onScanReceiver");
        // KTM OD128
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String scanResult = intent.getStringExtra(PARAM_BC_VAL);
                String bc_type = intent.getStringExtra(PARAM_BC_TYPE);
                /*String action = intent.getAction();
                Log.e("onReceive", "action:" + action);
                // Get scan results, including string and byte data etc.
                byte[] barcode = intent.getByteArrayExtra(DECODE_DATA_TAG);
                int barcodeLen = intent.getIntExtra(BARCODE_LENGTH_TAG, 0);
                byte temp = intent.getByteExtra(BARCODE_TYPE_TAG, (byte) 0);
                String barcodeStr = intent.getStringExtra(BARCODE_STRING_TAG);
                Log.i("barcode", "type:" + temp);
                String scanResult = new String(barcode, 0, barcodeLen);
                // print scan results.
                scanResult = " length：" + barcodeLen + "\nbarcode：" + scanResult + "\nbytesToHexString：" + bytesToHexString(barcode) + "\nbarcodeStr:" + barcodeStr;*/
                events.success(scanResult);
            }
        };
    }

    @Override
    public void onListen(Object o, final EventChannel.EventSink eventSink) {
        Log.i("UrovoScanPlugin", "onListen");
        boolean isSuccess = initScan();
        if (!isSuccess) {
            eventSink.error("init", "Init error", "scanner init");
            return;
        }
        this.receiver = onScanReceiver(eventSink);
        registerReceiver(true);

    }

    @Override
    public void onCancel(Object o) {
        Log.i("UrovoScanPlugin", "onCancel");
        registerReceiver(false);
        this.receiver = null;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        applicationContext = binding.getApplicationContext();
        eventChannel = new EventChannel(binding.getBinaryMessenger(), CHANNEL);
        eventChannel.setStreamHandler(this);

    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        registerReceiver(false);
        applicationContext = null;
        eventChannel.setStreamHandler(null);
        eventChannel = null;
        mScanManager = null;
    }

    /**
     * @param register , ture register , false unregister
     */
    private void registerReceiver(boolean register) {
        if(applicationContext==null){
            Log.e("registerReceiver", "applicationContext is null");
            return;
        }
        if (register && mScanManager != null) {
            IntentFilter filter = new IntentFilter();
            int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
            String[] value_buf = mScanManager.getParameterString(idbuf);
            if (value_buf != null && value_buf[0] != null && !value_buf[0].equals("")) {
                filter.addAction(value_buf[0]);
            } else {
                filter.addAction(ACTION_DECODE);
            }
            Log.i("registerReceiver", "applicationContext:" + applicationContext + ", receiver" + this.receiver + ", filter" + filter);
            this.applicationContext.registerReceiver(this.receiver, filter);
        } else if (mScanManager != null) {
            mScanManager.stopDecode();
            this.applicationContext.unregisterReceiver(this.receiver);
        }
    }

    private boolean initScan() {
        mScanManager = new ScanManager();
        boolean powerOn = mScanManager.getScannerState();
        Log.i("initScan", "powerOn1 " + powerOn);
        if (!powerOn) {
            powerOn = mScanManager.openScanner();
            Log.i("initScan", "powerOn " + powerOn);
        }
        return powerOn;
    }
}
