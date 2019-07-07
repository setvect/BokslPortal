import "jquery.cookie"
(function($, sr) {
  var debounce = function(func, threshold, execAsap) {
    var timeout

    return function debounced() {
      var obj = this,
        args = arguments
      function delayed() {
        if (!execAsap) func.apply(obj, args)
        timeout = null
      }

      if (timeout) clearTimeout(timeout)
      else if (execAsap) func.apply(obj, args)

      timeout = setTimeout(delayed, threshold || 100)
    }
  }

  // smartresize
  jQuery.fn[sr] = function(fn) {
    return fn ? this.bind("resize", debounce(fn)) : this.trigger(sr)
  }
})(jQuery, "smartresize")

const PageBody = {}

// Sidebar
PageBody.init_sidebar = function() {
  PageBody.CURRENT_URL = window.location.href.split("#")[0].split("?")[0]
  PageBody.$BODY = $("body")
  PageBody.$MENU_TOGGLE = $("#menu_toggle")
  PageBody.$SIDEBAR_MENU = $("#sidebar-menu")
  PageBody.$LEFT_COL = $(".left_col")
  PageBody.$RIGHT_COL = $(".right_col")
  PageBody.$NAV_MENU = $(".nav_menu")

  var setContentHeight = function() {
    // reset height
    PageBody.$RIGHT_COL.css("min-height", $(window).height())

    var bodyHeight = PageBody.$BODY.outerHeight(),
      contentHeight = bodyHeight

    // normalize content
    contentHeight -= PageBody.$NAV_MENU.height()
    PageBody.$RIGHT_COL.css("min-height", contentHeight + 57)
  }

  PageBody.$SIDEBAR_MENU.find("a").on("click", function(ev) {
    //  //  console.log('clicked - sidebar_menu');
    var $li = $(this).parent()

    if ($li.is(".active")) {
      $li.removeClass("active active-sm")
      $("ul:first", $li).slideUp(function() {
        setContentHeight()
      })
    } else {
      // prevent closing menu if we are on child menu
      if (!$li.parent().is(".child_menu")) {
        PageBody.$SIDEBAR_MENU.find("li").removeClass("active active-sm")
        PageBody.$SIDEBAR_MENU.find("li ul").slideUp()
      } else {
        if (PageBody.$BODY.is(".nav-sm")) {
          $li
            .parent()
            .find("li")
            .removeClass("active active-sm")
          $li
            .parent()
            .find("li ul")
            .slideUp()
        }
      }
      $li.addClass("active")

      $("ul:first", $li).slideDown(function() {
        setContentHeight()
      })
    }
  })

  // toggle small or large menu
  PageBody.$MENU_TOGGLE.on("click", function() {
    //  console.log('clicked - menu toggle');

    if (PageBody.$BODY.hasClass("nav-md")) {
      PageBody.$SIDEBAR_MENU.find("li.active ul").hide()
      PageBody.$SIDEBAR_MENU
        .find("li.active")
        .addClass("active-sm")
        .removeClass("active")
    } else {
      PageBody.$SIDEBAR_MENU.find("li.active-sm ul").show()
      PageBody.$SIDEBAR_MENU
        .find("li.active-sm")
        .addClass("active")
        .removeClass("active-sm")
    }

    PageBody.$BODY.toggleClass("nav-md")
    $.cookie("menu-small", PageBody.$BODY.hasClass("nav-sm"), { expires: 30, path: "/" })
    setContentHeight()

    $(".dataTable").each(function() {
      $(this)
        .dataTable()
        .fnDraw()
    })
  })

  // check active menu
  PageBody.$SIDEBAR_MENU
    .find('a[href="' + PageBody.CURRENT_URL + '"]')
    .parent("li")
    .addClass("current-page")

  PageBody.$SIDEBAR_MENU
    .find("a")
    .filter(function() {
      return this.href == PageBody.CURRENT_URL
    })
    .parent("li")
    .addClass("current-page")
    .parents("ul")
    .slideDown(function() {
      setContentHeight()
    })
    .parent()
    .addClass("active")

  // recompute content when resizing
  $(window).smartresize(function() {
    setContentHeight()
  })

  setContentHeight()

  // fixed sidebar
  if ($.fn.mCustomScrollbar) {
    $(".menu_fixed").mCustomScrollbar({
      autoHideScrollbar: true,
      theme: "minimal",
      mouseWheel: { preventDefault: true }
    })
  }
}

PageBody.init = function() {
  PageBody.init_sidebar()
  let menuSmall = $.cookie("menu-small") == "true"
  if (menuSmall) {
    PageBody.$MENU_TOGGLE.trigger("click")
  }
}

export default PageBody
