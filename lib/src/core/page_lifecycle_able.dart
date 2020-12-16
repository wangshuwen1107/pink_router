import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';
import '../util/pink_build_context.dart';
import 'navigator_proxy_widget.dart';
import 'page_route.dart';
import '../entity/pink_const.dart';

mixin PageLifecycleAble<T extends StatefulWidget> on State<T> {

  PinkPageRoute _pageRoute;

  _PageLifeCycleObserver _pageObserver;

  @override
  void initState() {
    super.initState();
    final state = context.stateOf<NavigatorProxyWidgetState>();
    _pageRoute = state.pageRouteList.last;
    if (null != _pageRoute) {
      _pageObserver = _PageLifeCycleObserver(_pageRoute, this);
    }
    PinkRouter.registerPageObserver(_pageObserver);

    WidgetsBinding.instance.addPostFrameCallback((time) {
      _pageObserver.create(_pageRoute.settings);
      _pageObserver.willAppear(_pageRoute.settings);
      _pageObserver.didAppear(_pageRoute.settings);
    });
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
  }

  @override
  void dispose() {
    super.dispose();
    PinkRouter.unRegisterPageObserver(_pageObserver);
  }

  void create(RouteSettings routeSettings) {}

  void willAppear(RouteSettings routeSettings) {}

  void didAppear(RouteSettings routeSettings) {}

  void willDisappear(RouteSettings routeSettings) {}

  void didDisappear(RouteSettings routeSettings) {}

  void destroy(RouteSettings routeSettings) {}
}

class _PageLifeCycleObserver with PageLifeCycleObserver {
  final PinkPageRoute pageRoute;
  final PageLifecycleAble pageLifecycleAble;
  PageLifeCycle _pageLifeCycleStatus;

  _PageLifeCycleObserver(this.pageRoute, this.pageLifecycleAble);

  @override
  void create(RouteSettings routeSettings) {
    if (pageRoute.settings.name == routeSettings.name) {
      if (null == _pageLifeCycleStatus) {
        pageLifecycleAble.create(routeSettings);
        _pageLifeCycleStatus = PageLifeCycle.create;
      }
    }
  }

  @override
  void willAppear(RouteSettings routeSettings) {
    if (pageRoute.settings.name == routeSettings.name) {
      if (PageLifeCycle.create == _pageLifeCycleStatus ||
          PageLifeCycle.didDisappear == _pageLifeCycleStatus) {
        pageLifecycleAble.willAppear(routeSettings);
        _pageLifeCycleStatus = PageLifeCycle.willAppear;
      }
    }
  }

  @override
  void didAppear(RouteSettings routeSettings) {
    if (pageRoute.settings.name == routeSettings.name) {
      if (PageLifeCycle.willAppear == _pageLifeCycleStatus) {
        pageLifecycleAble.didAppear(routeSettings);
        _pageLifeCycleStatus = PageLifeCycle.didAppear;
      }
    }
  }

  @override
  void willDisappear(RouteSettings routeSettings) {
    if (pageRoute.settings.name == routeSettings.name) {
      if (PageLifeCycle.didAppear == _pageLifeCycleStatus) {
        pageLifecycleAble.willDisappear(routeSettings);
        _pageLifeCycleStatus = PageLifeCycle.willDisappear;
      }
    }
  }

  @override
  void didDisappear(RouteSettings routeSettings) {
    if (pageRoute.settings.name == routeSettings.name) {
      if (PageLifeCycle.willDisappear == _pageLifeCycleStatus) {
        pageLifecycleAble.didDisappear(routeSettings);
        _pageLifeCycleStatus = PageLifeCycle.didDisappear;
      }
    }
  }

  @override
  void destroy(RouteSettings routeSettings) {
    if (pageRoute.settings.name == routeSettings.name) {
      if (PageLifeCycle.didDisappear == _pageLifeCycleStatus) {
        pageLifecycleAble.destroy(routeSettings);
        _pageLifeCycleStatus = PageLifeCycle.destroy;
      }
    }
  }
}
