part of 'home_bloc_bloc.dart';

abstract class HomeBlocEvent extends Equatable {
  const HomeBlocEvent();

  @override
  List<Object> get props => [];
}

// Event to start the service
class StartServiceEvent extends HomeBlocEvent {
  const StartServiceEvent();

  @override
  List<Object> get props => [];
}

// Overlay permission result event
class OverlayPermissionResultEvent extends HomeBlocEvent {
  final bool isGranted;
  const OverlayPermissionResultEvent({required this.isGranted});

  @override
  List<Object> get props => [isGranted];
}
