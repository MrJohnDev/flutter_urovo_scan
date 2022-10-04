import 'package:flutter/material.dart';
import 'dart:async';

import 'package:urovo_scan/urovo_scan.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late StreamSubscription<String> _scanCodeStream;
  String code = '';
  final _urovoScan = UrovoScan();

  @override
  void initState() {
    super.initState();
    _scanCodeStream = _urovoScan.onScanCodeChanged.listen((String state) {
      print("state"+state);
      if (!mounted) return;
      setState(() {
        code = state;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text(key:const Key('scan_result'),'Scan code: $code\n'),
        ),
      ),
    );
  }
}
