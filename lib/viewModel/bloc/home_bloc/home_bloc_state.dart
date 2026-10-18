part of 'home_bloc_bloc.dart';

class HomeBlocState extends Equatable {
  final bool isOverlayPermissionGranted;
  const HomeBlocState({this.isOverlayPermissionGranted = false});

  HomeBlocState copyWith({bool? isOverlayPermissionGranted}) {
    return HomeBlocState(
      isOverlayPermissionGranted:
          isOverlayPermissionGranted ?? this.isOverlayPermissionGranted,
    );
  }

  @override
  List<Object> get props => [isOverlayPermissionGranted];
}
