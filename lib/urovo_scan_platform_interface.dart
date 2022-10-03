import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'urovo_scan_method_channel.dart';

abstract class UrovoScanPlatform extends PlatformInterface {
  /// Constructs a UrovoScanPlatform.
  UrovoScanPlatform() : super(token: _token);

  static final Object _token = Object();

  static UrovoScanPlatform _instance = MethodChannelUrovoScan();

  /// The default instance of [UrovoScanPlatform] to use.
  ///
  /// Defaults to [MethodChannelUrovoScan].
  static UrovoScanPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [UrovoScanPlatform] when
  /// they register themselves.
  static set instance(UrovoScanPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
