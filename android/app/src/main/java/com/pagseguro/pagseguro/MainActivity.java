package com.pagseguro.pagseguro;

import android.content.*;
import androidx.annotation.*;
import com.pagseguro.pagseguro.core.*;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.*;
import io.flutter.plugin.common.*;

public class MainActivity extends FlutterActivity {

    static private final String CHANNEL_NAME = "pagseguro";
    private MethodChannel channel;
    private PagseguroService pagseguroService;
    private Context context;


    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        context = getApplicationContext();
        channel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME);
        pagseguroService = new PagseguroService(context, channel);
        channel.setMethodCallHandler((call, result) -> {
            if (call.method.startsWith("auth")) pagseguroService.initAuth(call, result);
            else if (call.method.startsWith("payment")) pagseguroService.initPayment(call, result);
            else result.notImplemented();
        });

    }

    @Override
    public void onDestroy() {
        channel.setMethodCallHandler(null);
        channel = null;
        pagseguroService.dispose();
        pagseguroService = null;
        context = null;
        super.onDestroy();
    }
}
