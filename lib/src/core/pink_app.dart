import 'package:flutter/material.dart';
import 'package:pink_router/src/core/pink_router_wrapper.dart';

class PinkApp extends MaterialApp {
  PinkApp({
    Key key,
    String title = '',
    GenerateAppTitle onGenerateTitle,
    Color color,
    ThemeData theme,
    ThemeData darkTheme,
    ThemeMode themeMode = ThemeMode.system,
    Locale locale,
    Iterable<LocalizationsDelegate<dynamic>> localizationsDelegates,
    LocaleListResolutionCallback localeListResolutionCallback,
    LocaleResolutionCallback localeResolutionCallback,
    Iterable<Locale> supportedLocales = const <Locale>[Locale('en', 'US')],
    bool debugShowMaterialGrid = false,
    bool showPerformanceOverlay = false,
    bool checkerboardRasterCacheImages = false,
    bool checkerboardOffscreenLayers = false,
    bool showSemanticsDebugger = false,
    bool debugShowCheckedModeBanner = true,
    Map<LogicalKeySet, Intent> shortcuts,
    Map<LocalKey, ActionFactory> actions,
  }) : super(
            key: key,
            navigatorKey: PinkRouterWrapper.navigatorKey,
            navigatorObservers: [],
            title: title,
            onGenerateTitle: onGenerateTitle,
            color: color,
            theme: theme,
            home: Container(
              color: Colors.white
            ),
            darkTheme: darkTheme,
            themeMode: themeMode,
            locale: locale,
            localizationsDelegates: localizationsDelegates,
            localeListResolutionCallback: localeListResolutionCallback,
            localeResolutionCallback: localeResolutionCallback,
            supportedLocales: supportedLocales,
            debugShowMaterialGrid: debugShowMaterialGrid,
            showPerformanceOverlay: showPerformanceOverlay,
            checkerboardRasterCacheImages: checkerboardRasterCacheImages,
            checkerboardOffscreenLayers: checkerboardOffscreenLayers,
            showSemanticsDebugger: showSemanticsDebugger,
            debugShowCheckedModeBanner: debugShowCheckedModeBanner,
            shortcuts: shortcuts,
            actions: actions);
}
