import 'package:flutter/material.dart';
import 'navigator_page_observer.dart';
import '../../entity/pink_const.dart';

class NavigatorPageObserverManager {

  static List<PageLifeCycleObserver> get observerList => _observerList;

  static List<PageLifeCycleObserver> _observerList = [];

  static void registerPageObserver(PageLifeCycleObserver observer) {
    _observerList.add(observer);
  }

  static void unRegisterPageObserver(PageLifeCycleObserver observer) {
    _observerList.remove(observer);
  }

  static void create(RouteSettings settings) {
    if (settings.name == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    _observerList.forEach((observer) {
      observer.create(settings);
    });
  }

  static void willAppear(RouteSettings settings) {
    if (settings.name == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    _observerList.forEach((observer) {
      observer.willAppear(settings);
    });
  }

  static void didAppear(RouteSettings settings) {
    if (settings.name == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    _observerList.forEach((observer) {
      observer.didAppear(settings);
    });
  }

  static void willDisappear(RouteSettings settings) {
    if (settings.name == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    _observerList.forEach((observer) {
      observer.willDisappear(settings);
    });
  }

  static void didDisappear(RouteSettings settings) {
    if (settings.name == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    _observerList.forEach((observer) {
      observer.didDisappear(settings);
    });
  }

  static void destroy(RouteSettings settings) {
    if (settings.name == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    _observerList.forEach((observer) {
      observer.destroy(settings);
    });
  }
}
