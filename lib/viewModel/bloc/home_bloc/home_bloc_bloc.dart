import 'dart:async';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:piko_typo_assistant/services/typo_method_channel.dart';

part 'home_bloc_event.dart';
part 'home_bloc_state.dart';

class HomeBloc extends Bloc<HomeBlocEvent, HomeBlocState> {
  final TypoMethodChannel _typoMethodChannel;
  late final StreamSubscription<bool> _streamSubscription;
  HomeBloc({required this._typoMethodChannel}) : super(HomeBlocState()) {
    on<StartServiceEvent>(_startService);
    on<OverlayPermissionResultEvent>(_handleOverlayPermissionResult);

    _streamSubscription = _typoMethodChannel.overlayStream.listen((isGranted) {
      add(OverlayPermissionResultEvent(isGranted: isGranted));
    });
  }

  Future<void> _startService(
    StartServiceEvent event,
    Emitter<HomeBlocState> emit,
  ) async {
    await _typoMethodChannel.requestOverlayPermission();
  }

  void _handleOverlayPermissionResult(
    OverlayPermissionResultEvent event,
    Emitter<HomeBlocState> emit,
  ) {
    emit(state.copyWith(isOverlayPermissionGranted: event.isGranted));
  }

  @override
  Future<void> close() async {
    await _streamSubscription.cancel();
    _typoMethodChannel.dispose();

    return super.close();
  }
}
