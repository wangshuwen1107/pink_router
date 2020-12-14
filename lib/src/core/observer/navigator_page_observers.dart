import 'package:flutter/material.dart';
import 'navigator_page_observer.dart';

class NavigatorPageObserverManager {

  static List<PageLifeCycleObserver> get observerList => _observerList;

  static List<PageLifeCycleObserver> _observerList = [];

  static void addLifeCycleObserver(PageLifeCycleObserver observer) {
    _observerList.add(observer);
  }

  static void create(RouteSettings settings) {
    if (settings.name == "/") {
      return;
    }
    _observerList.forEach((observer) {
      observer.create(settings);
    });
  }

  static void willAppear(RouteSettings settings) {
    if (settings.name == "/") {
      return;
    }
    _observerList.forEach((observer) {
      observer.willAppear(settings);
    });
  }

  static void didAppear(RouteSettings settings) {
    if (settings.name == "/") {
      return;
    }
    _observerList.forEach((observer) {
      observer.didAppear(settings);
    });
  }

  static void willDisappear(RouteSettings settings) {
    if (settings.name == "/") {
      return;
    }
    _observerList.forEach((observer) {
      observer.willDisappear(settings);
    });
  }

  static void didDisappear(RouteSettings settings) {
    if (settings.name == "/") {
      return;
    }
    _observerList.forEach((observer) {
      observer.didDisappear(settings);
    });
  }

  static void destroy(RouteSettings settings) {
    if (settings.name == "/") {
      return;
    }
    _observerList.forEach((observer) {
      observer.destroy(settings);
    });
  }
}
