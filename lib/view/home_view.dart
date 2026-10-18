import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:piko_typo_assistant/services/typo_method_channel.dart';
import 'package:piko_typo_assistant/viewModel/bloc/home_bloc/home_bloc_bloc.dart';

class HomeView extends StatefulWidget {
  const HomeView({super.key});

  @override
  State<HomeView> createState() => _HomeViewState();
}

class _HomeViewState extends State<HomeView> {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) => HomeBloc(typoMethodChannel: TypoMethodChannel()),
      child: Scaffold(
        body: Center(
          child: BlocBuilder<HomeBloc, HomeBlocState>(
            buildWhen: (previous, current) =>
                previous.isOverlayPermissionGranted !=
                current.isOverlayPermissionGranted,
            builder: (context, state) {
              return ElevatedButton(
                onPressed: () {
                  context.read<HomeBloc>().add(const StartServiceEvent());
                },
                child: Text(
                  state.isOverlayPermissionGranted
                      ? 'Running!!'
                      : 'Start Service',
                ),
              );
            },
          ),
        ),
      ),
    );
  }
}
