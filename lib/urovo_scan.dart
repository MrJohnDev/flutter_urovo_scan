
import 'urovo_scan_platform_interface.dart';

class UrovoScan {
  Future<String?> getPlatformVersion() {
    return UrovoScanPlatform.instance.getPlatformVersion();
  }
}
