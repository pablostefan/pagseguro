import 'package:flutter/material.dart';

class ButtonWidget extends StatelessWidget {
  final String text;
  final GestureTapCallback onTap;

  const ButtonWidget({super.key, required this.text, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: onTap,
        child: Container(
            padding: const EdgeInsets.all(20),
            margin: const EdgeInsets.all(20),
            decoration: BoxDecoration(color: Colors.indigoAccent, borderRadius: BorderRadius.circular(10)),
            child: Text(text, style: const TextStyle(color: Colors.white, fontSize: 30))));
  }
}
