import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';

part 'home_bloc_event.dart';
part 'home_bloc_state.dart';

class HomeBloc extends Bloc<HomeBlocEvent, HomeBlocState> {
  HomeBloc() : super(HomeBlocState()) {
    on<StartServiceEvent>(_startService);
  }

  Future<void> _startService(HomeBlocEvent event, Emitter<HomeBlocState> emit) async {
    emit(state.copyWith(isServiceRunning: true));
  }
}
