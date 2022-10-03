import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'urovo_scan_platform_interface.dart';

/// An implementation of [UrovoScanPlatform] that uses method channels.
class MethodChannelUrovoScan extends UrovoScanPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('urovo_scan');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
