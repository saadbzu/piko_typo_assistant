import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
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
      create: (context) => HomeBloc(),
      child: Center(
        child: ElevatedButton(
          onPressed: () {
            context.read<HomeBloc>().add(const StartServiceEvent());
          },
          child: BlocBuilder<HomeBloc, HomeBlocState>(
            buildWhen: (previous, current) =>
                previous.isServiceRunning != current.isServiceRunning,
            builder: (context, state) {
              return Text(
                state.isServiceRunning ? 'Service Running' : 'Start Service',
              );
            },
          ),
        ),
      ),
    );
  }
}
