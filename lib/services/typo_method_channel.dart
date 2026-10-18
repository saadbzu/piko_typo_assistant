import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class TypoMethodChannel {
  final MethodChannel _typeChannel = const MethodChannel('typo_assistant');

  TypoMethodChannel() {
    _typeChannel.setMethodCallHandler(_handleMethodCall);
  }

  final StreamController<bool> _overLayController =
      StreamController<bool>.broadcast();

  Stream<bool> get overlayStream => _overLayController.stream;

  Future<void> requestOverlayPermission() async {
    try {
      await _typeChannel.invokeMethod('requestOverlayPermission');
    } on PlatformException catch (e) {
      debugPrint('Failed to request overlay permission: ${e.message}');
    }
  }

  Future<void> _handleMethodCall(MethodCall call) async {
    if (call.method == 'overlayPermissionResult') {
      final bool isGranted = call.arguments as bool;

      if (!_overLayController.isClosed) {
        _overLayController.add(isGranted);
      }
    }
  }

  void dispose() {
    _overLayController.close();
  }
}
