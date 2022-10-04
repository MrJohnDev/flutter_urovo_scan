
import 'urovo_scan_platform_interface.dart';

class UrovoScan {
  /// Fires whenever the battery state changes.
  Stream<String> get onScanCodeChanged =>
      UrovoScanPlatform.instance.onScanCodeChanged();
}
