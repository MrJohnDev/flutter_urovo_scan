import 'package:flutter_test/flutter_test.dart';
import 'package:urovo_scan/urovo_scan.dart';
import 'package:urovo_scan/urovo_scan_platform_interface.dart';
import 'package:urovo_scan/urovo_scan_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockUrovoScanPlatform
    with MockPlatformInterfaceMixin
    implements UrovoScanPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final UrovoScanPlatform initialPlatform = UrovoScanPlatform.instance;

  test('$MethodChannelUrovoScan is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelUrovoScan>());
  });

  test('getPlatformVersion', () async {
    UrovoScan urovoScanPlugin = UrovoScan();
    MockUrovoScanPlatform fakePlatform = MockUrovoScanPlatform();
    UrovoScanPlatform.instance = fakePlatform;

    expect(await urovoScanPlugin.getPlatformVersion(), '42');
  });
}
