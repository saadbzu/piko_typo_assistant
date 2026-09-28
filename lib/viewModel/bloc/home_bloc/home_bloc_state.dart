part of 'home_bloc_bloc.dart';

class HomeBlocState extends Equatable {
  final bool isServiceRunning;
  const HomeBlocState({this.isServiceRunning = false});

  HomeBlocState copyWith({bool? isServiceRunning}) {
    return HomeBlocState(
      isServiceRunning: isServiceRunning ?? this.isServiceRunning,
    );
  }

  @override
  List<Object> get props => [isServiceRunning];
}
