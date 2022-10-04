import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'urovo_scan_platform_interface.dart';

/// An implementation of [UrovoScanPlatform] that uses method channels.
class MethodChannelUrovoScan extends UrovoScanPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final EventChannel eventChannel = const EventChannel("com.mrjohndev.urovo_scan/plugin");

  Stream<String>? _onScanCodeChanged;

  Stream<String> onScanCodeChanged() {
    _onScanCodeChanged ??= eventChannel
          .receiveBroadcastStream()
          .map((dynamic event) => (event));
    return _onScanCodeChanged!;
  }
}
