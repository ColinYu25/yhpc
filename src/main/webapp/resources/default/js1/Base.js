/*!
* KSN JS Library 1.0.1
* Copyright(c) 2006-2010 KSN, Inc.
* wanmw@126.com
*/
// for old browsers
window.undefined = window.undefined;

/**
* @class KSN
* KSN core utilities and functions.
* @singleton
*/
KSN = {
    version: '3.3.0',
    versionDetail: {
        major: 3,
        minor: 3,
        patch: 0
    }
};

/**
* 复制所有的属性到obj的配置.
* @param {o} obj的接收器的属性
* @param {c} 配置属性的来源
* @param {defaults} 默认一个不同的对象，也将适用于为默认值
* @return {Object} returns obj
* @member KSN apply
*/
KSN.apply = function(o, c, defaults) {
    // no "this" reference for friendly out of scope calls
    if (defaults) {
        KSN.apply(o, defaults);
    }
    if (o && c && typeof c == 'object') {
        for (var p in c) {
            o[p] = c[p];
        }
    }
    return o;
};

(function () {
    var idSeed = 0,
        toString = Object.prototype.toString,
        ua = navigator.userAgent.toLowerCase(),
        check = function (r) {
            return r.test(ua);
        },
        DOC = document,
        docMode = DOC.documentMode,
        isStrict = DOC.compatMode == "CSS1Compat",
        isOpera = check(/opera/),
        isChrome = check(/\bchrome\b/),
        isWebKit = check(/webkit/),
        isSafari = !isChrome && check(/safari/),
        isSafari2 = isSafari && check(/applewebkit\/4/), // unique to Safari 2
        isSafari3 = isSafari && check(/version\/3/),
        isSafari4 = isSafari && check(/version\/4/),
        isIE = !isOpera && check(/msie/),
        isIE7 = isIE && (check(/msie 7/) || docMode == 7),
        isIE8 = isIE && (check(/msie 8/) && docMode != 7),
        isIE6 = isIE && !isIE7 && !isIE8,
        isGecko = !isWebKit && check(/gecko/),
        isGecko2 = isGecko && check(/rv:1\.8/),
        isGecko3 = isGecko && check(/rv:1\.9/),
        isBorderBox = isIE && !isStrict,
        isWindows = check(/windows|win32/),
        isMac = check(/macintosh|mac os x/),
        isAir = check(/adobeair/),
        isLinux = check(/linux/),
        isSecure = /^https/i.test(window.location.protocol);

    // remove css image flicker
    if (isIE6) {
        try {
            DOC.execCommand("BackgroundImageCache", false, true);
        } catch (e) { }
    }

    KSN.apply(KSN, {
        /**
        * URL to a blank file used by KSN when in secure mode for iframe src and onReady src to prevent
        * the IE insecure content warning (<tt>'about:blank'</tt>, except for IE in secure mode, which is <tt>'javascript:""'</tt>).
        * @type String
        */
        SSL_SECURE_URL: isSecure && isIE ? 'javascript:""' : 'about:blank',
        /**
        * True if the browser is in strict (standards-compliant) mode, as opposed to quirks mode
        * @type Boolean
        */
        isStrict: isStrict,
        /**
        * True if the page is running over SSL
        * @type Boolean
        */
        isSecure: isSecure,
        /**
        * True when the document is fully initialized and ready for action
        * @type Boolean
        */
        isReady: false,

        /**
        * True if the {@link KSN.Fx} Class is available
        * @type Boolean
        * @property enableFx
        */

        /**
        * HIGHLY EXPERIMENTAL
        * True to force css based border-box model override and turning off javascript based adjustments. This is a
        * runtime configuration and must be set before onReady.
        * @type Boolean
        */
        enableForcedBoxModel: false,

        /**
        * True to automatically uncache orphaned KSN.Elements periodically (defaults to true)
        * @type Boolean
        */
        enableGarbageCollector: true,

        /**
        * True to automatically purge event listeners during garbageCollection (defaults to false).
        * @type Boolean
        */
        enableListenerCollection: false,

        /**
        * EXPERIMENTAL - True to cascade listener removal to child elements when an element is removed.
        * Currently not optimized for performance.
        * @type Boolean
        */
        enableNestedListenerRemoval: false,

        /**
        * Indicates whether to use native browser parsing for JSON methods.
        * This option is ignored if the browser does not support native JSON methods.
        * <b>Note: Native JSON methods will not work with objects that have functions.
        * Also, property names must be quoted, otherwise the data will not parse.</b> (Defaults to false)
        * @type Boolean
        */
        USE_NATIVE_JSON: false,

        /**
        * Copies all the properties of config to obj if they don't already exist.
        * @param {Object} obj The receiver of the properties
        * @param {Object} config The source of the properties
        * @return {Object} returns obj
        */
        applyIf: function (o, c) {
            if (o) {
                for (var p in c) {
                    if (!KSN.isDefined(o[p])) {
                        o[p] = c[p];
                    }
                }
            }
            return o;
        },

        /**
        * Generates unique ids. If the element already has an id, it is unchanged
        * @param {Mixed} el (optional) The element to generate an id for
        * @param {String} prefix (optional) Id prefix (defaults "KSN-gen")
        * @return {String} The generated Id.
        */
        id: function (el, prefix) {
            el = KSN.getDom(el, true) || {};
            if (!el.id) {
                el.id = (prefix || "KSN-gen") + (++idSeed);
            }
            return el.id;
        },

        /**
        * Adds a list of functions to the prototype of an existing class, overwriting any existing methods with the same name.
        * Usage:<pre><code>
        KSN.override(MyClass, {
        newMethod1: function(){
        // etc.
        },
        newMethod2: function(foo){
        // etc.
        }
        });
        </code></pre>
        * @param {Object} origclass The class to override
        * @param {Object} overrides The list of functions to add to origClass.  This should be specified as an object literal
        * containing one or more methods.
        * @method override
        */
        override: function (origclass, overrides) {
            if (overrides) {
                var p = origclass.prototype;
                KSN.apply(p, overrides);
                if (KSN.isIE && overrides.hasOwnProperty('toString')) {
                    p.toString = overrides.toString;
                }
            }
        },

        /**
        * Creates namespaces to be used for scoping variables and classes so that they are not global.
        * Specifying the last node of a namespace implicitly creates all other nodes. Usage:
        * <pre><code>
        KSN.namespace('Company', 'Company.data');
        KSN.namespace('Company.data'); // equivalent and preferable to above syntax
        Company.Widget = function() { ... }
        Company.data.CustomStore = function(config) { ... }
        </code></pre>
        * @param {String} namespace1
        * @param {String} namespace2
        * @param {String} etc
        * @return {Object} The namespace object. (If multiple arguments are passed, this will be the last namespace created)
        * @method namespace
        */
        namespace: function () {
            var o, d;
            KSN.each(arguments, function (v) {
                d = v.split(".");
                o = window[d[0]] = window[d[0]] || {};
                KSN.each(d.slice(1), function (v2) {
                    o = o[v2] = o[v2] || {};
                });
            });
            return o;
        },

        /**
        * Takes an object and converts it to an encoded URL. e.g. KSN.urlEncode({foo: 1, bar: 2}); would return "foo=1&bar=2".  Optionally, property values can be arrays, instead of keys and the resulting string that's returned will contain a name/value pair for each array value.
        * @param {Object} o
        * @param {String} pre (optional) A prefix to add to the url encoded string
        * @return {String}
        */
        urlEncode: function (o, pre) {
            var empty,
                buf = [],
                e = encodeURIComponent;

            KSN.iterate(o, function (key, item) {
                empty = KSN.isEmpty(item);
                KSN.each(empty ? key : item, function (val) {
                    buf.push('&', e(key), '=', (!KSN.isEmpty(val) && (val != key || !empty)) ? (KSN.isDate(val) ? KSN.encode(val).replace(/"/g, '') : e(val)) : '');
                });
            });
            if (!pre) {
                buf.shift();
                pre = '';
            }
            return pre + buf.join('');
        },

        /**
        * Takes an encoded URL and and converts it to an object. Example: <pre><code>
        KSN.urlDecode("foo=1&bar=2"); // returns {foo: "1", bar: "2"}
        KSN.urlDecode("foo=1&bar=2&bar=3&bar=4", false); // returns {foo: "1", bar: ["2", "3", "4"]}
        </code></pre>
        * @param {String} string
        * @param {Boolean} overwrite (optional) Items of the same name will overwrite previous values instead of creating an an array (Defaults to false).
        * @return {Object} A literal with members
        */
        urlDecode: function (string, overwrite) {
            if (KSN.isEmpty(string)) {
                return {};
            }
            var obj = {},
                pairs = string.split('&'),
                d = decodeURIComponent,
                name,
                value;
            KSN.each(pairs, function (pair) {
                pair = pair.split('=');
                name = d(pair[0]);
                value = d(pair[1]);
                obj[name] = overwrite || !obj[name] ? value :
                            [].concat(obj[name]).concat(value);
            });
            return obj;
        },

        /**
        * Appends content to the query string of a URL, handling logic for whether to place
        * a question mark or ampersand.
        * @param {String} url The URL to append to.
        * @param {String} s The content to append to the URL.
        * @return (String) The resulting URL
        */
        urlAppend: function (url, s) {
            if (!KSN.isEmpty(s)) {
                return url + (url.indexOf('?') === -1 ? '?' : '&') + s;
            }
            return url;
        },

        /**
        * Converts any iterable (numeric indices and a length property) into a true array
        * Don't use this on strings. IE doesn't support "abc"[0] which this implementation depends on.
        * For strings, use this instead: "abc".match(/./g) => [a,b,c];
        * @param {Iterable} the iterable object to be turned into a true Array.
        * @return (Array) array
        */
        toArray: function () {
            return isIE ?
                 function (a, i, j, res) {
                     res = [];
                     for (var x = 0, len = a.length; x < len; x++) {
                         res.push(a[x]);
                     }
                     return res.slice(i || 0, j || res.length);
                 } :
                 function (a, i, j) {
                     return Array.prototype.slice.call(a, i || 0, j || a.length);
                 };
        } (),

        isIterable: function (v) {
            //check for array or arguments
            if (KSN.isArray(v) || v.callee) {
                return true;
            }
            //check for node list type
            if (/NodeList|HTMLCollection/.test(toString.call(v))) {
                return true;
            }
            //NodeList has an item and length property
            //IXMLDOMNodeList has nextNode method, needs to be checked first.
            return ((typeof v.nextNode != 'undefined' || v.item) && KSN.isNumber(v.length));
        },

        /**
        * Iterates an array calling the supplied function.
        * @param {Array/NodeList/Mixed} array The array to be iterated. If this
        * argument is not really an array, the supplied function is called once.
        * @param {Function} fn The function to be called with each item. If the
        * supplied function returns false, iteration stops and this method returns
        * the current <code>index</code>. This function is called with
        * the following arguments:
        * <div class="mdetail-params"><ul>
        * <li><code>item</code> : <i>Mixed</i>
        * <div class="sub-desc">The item at the current <code>index</code>
        * in the passed <code>array</code></div></li>
        * <li><code>index</code> : <i>Number</i>
        * <div class="sub-desc">The current index within the array</div></li>
        * <li><code>allItems</code> : <i>Array</i>
        * <div class="sub-desc">The <code>array</code> passed as the first
        * argument to <code>KSN.each</code>.</div></li>
        * </ul></div>
        * @param {Object} scope The scope (<code>this</code> reference) in which the specified function is executed.
        * Defaults to the <code>item</code> at the current <code>index</code>
        * within the passed <code>array</code>.
        * @return See description for the fn parameter.
        */
        each: function (array, fn, scope) {
            if (KSN.isEmpty(array, true)) {
                return;
            }
            if (!KSN.isIterable(array) || KSN.isPrimitive(array)) {
                array = [array];
            }
            for (var i = 0, len = array.length; i < len; i++) {
                if (fn.call(scope || array[i], array[i], i, array) === false) {
                    return i;
                };
            }
        },

        /**
        * Iterates either the elements in an array, or each of the properties in an object.
        * <b>Note</b>: If you are only iterating arrays, it is better to call {@link #each}.
        * @param {Object/Array} object The object or array to be iterated
        * @param {Function} fn The function to be called for each iteration.
        * The iteration will stop if the supplied function returns false, or
        * all array elements / object properties have been covered. The signature
        * varies depending on the type of object being interated:
        * <div class="mdetail-params"><ul>
        * <li>Arrays : <tt>(Object item, Number index, Array allItems)</tt>
        * <div class="sub-desc">
        * When iterating an array, the supplied function is called with each item.</div></li>
        * <li>Objects : <tt>(String key, Object value, Object)</tt>
        * <div class="sub-desc">
        * When iterating an object, the supplied function is called with each key-value pair in
        * the object, and the iterated object</div></li>
        * </ul></div>
        * @param {Object} scope The scope (<code>this</code> reference) in which the specified function is executed. Defaults to
        * the <code>object</code> being iterated.
        */
        iterate: function (obj, fn, scope) {
            if (KSN.isEmpty(obj)) {
                return;
            }
            if (KSN.isIterable(obj)) {
                KSN.each(obj, fn, scope);
                return;
            } else if (typeof obj == 'object') {
                for (var prop in obj) {
                    if (obj.hasOwnProperty(prop)) {
                        if (fn.call(scope || obj, prop, obj[prop], obj) === false) {
                            return;
                        };
                    }
                }
            }
        },

        /**
        * Return the dom node for the passed String (id), dom node, or KSN.Element.
        * Optional 'strict' flag is needed for IE since it can return 'name' and
        * 'id' elements by using getElementById.
        * Here are some examples:
        * <pre><code>
        // gets dom node based on id
        var elDom = KSN.getDom('elId');
        // gets dom node based on the dom node
        var elDom1 = KSN.getDom(elDom);

        // If we don&#39;t know if we are working with an
        // KSN.Element or a dom node use KSN.getDom
        function(el){
        var dom = KSN.getDom(el);
        // do something with the dom node
        }
        * </code></pre>
        * <b>Note</b>: the dom node to be found actually needs to exist (be rendered, etc)
        * when this method is called to be successful.
        * @param {Mixed} el
        * @return HTMLElement
        */
        getDom: function (el, strict) {
            if (!el || !DOC) {
                return null;
            }
            if (el.dom) {
                return el.dom;
            } else {
                if (typeof el == 'string') {
                    var e = DOC.getElementById(el);
                    // IE returns elements with the 'name' and 'id' attribute.
                    // we do a strict check to return the element with only the id attribute
                    if (e && isIE && strict) {
                        if (el == e.getAttribute('id')) {
                            return e;
                        } else {
                            return null;
                        }
                    }
                    return e;
                } else {
                    return el;
                }
            }
        },

        /**
        * Returns the current document body as an {@link KSN.Element}.
        * @return KSN.Element The document body
        */
        getBody: function () {
            return KSN.get(DOC.body || DOC.documentElement);
        },

        /**
        * Returns the current document body as an {@link KSN.Element}.
        * @return KSN.Element The document body
        */
        getHead: function () {
            var head;

            return function () {
                if (head == undefined) {
                    head = KSN.get(DOC.getElementsByTagName("head")[0]);
                }

                return head;
            };
        } (),

        /**
        * get ifrmame's document
        */
        getIframeDom: function (el) {
            if (typeof el == 'string') {
                return DOC.getElementById(id).contentDocument || DOC.frames[id].document;
            } else {
                return el.contentDocument || el.document;
            }
        },

        /**
        * <p>Returns true if the passed value is empty.</p>
        * <p>The value is deemed to be empty if it is<div class="mdetail-params"><ul>
        * <li>null</li>
        * <li>undefined</li>
        * <li>an empty array</li>
        * <li>a zero length string</li>
        * </ul></div>
        * @param {Mixed} value The value to test
        * @param {Boolean} allowBlank (optional) true to allow empty strings (defaults to false)
        * @return {Boolean}
        */
        isEmpty: function (str) {
            if (str === null) { return true; }
            else if (str === undefined) { return true; }
            else if (typeof (str) == "undefined") { return true; }
            else if (str == "") { return true; } //空字符串也是空
            else if (typeof (str) == "object") { //有的时候str === undefined会失效所以用这种方法来验证,只有一个空对象没有属性
                var flag = true;
                for (var p in str) {
                    flag = false;
                    break;
                }
                if (flag) { return true; }
            }
            else if (str.constructor == Array && str.length == 0) { return true; } //空数组也是空
            else if (str.constructor == Number && isNaN(str)) { return true; } //不是数字也是空  

            return false;
        },

        /**
        * Returns true if the passed value is a JavaScript array, otherwise false.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isArray: function (v) {
            return toString.apply(v) === '[object Array]';
        },

        /**
        * Returns true if the passed object is a JavaScript date object, otherwise false.
        * @param {Object} object The object to test
        * @return {Boolean}
        */
        isDate: function (v) {
            return toString.apply(v) === '[object Date]';
        },

        /**
        * Returns true if the passed value is a JavaScript Object, otherwise false.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isObject: function (v) {
            return !!v && Object.prototype.toString.call(v) === '[object Object]';
        },

        /**
        * Returns true if the passed value is a JavaScript 'primitive', a string, number or boolean.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isPrimitive: function (v) {
            return KSN.isString(v) || KSN.isNumber(v) || KSN.isBoolean(v);
        },

        /**
        * Returns true if the passed value is a JavaScript Function, otherwise false.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isFunction: function (v) {
            return toString.apply(v) === '[object Function]';
        },

        /**
        * Returns true if the passed value is a number. Returns false for non-finite numbers.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isNumber: function (v) {
            if (v == null || v == '') return false;
            if (isNaN(v)) return false;
            return true;
        },

        /**
        * Returns true if the passed value is a string.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isString: function (v) {
            return typeof v === 'string';
        },

        /**
        * Returns true if the passed value is a boolean.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isBoolean: function (v) {
            return typeof v === 'boolean';
        },

        /**
        * Returns true if the passed value is an HTMLElement
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isElement: function (v) {
            return v ? !!v.tagName : false;
        },

        /**
        * Returns true if the passed value is not undefined.
        * @param {Mixed} value The value to test
        * @return {Boolean}
        */
        isDefined: function (v) {
            return typeof v !== 'undefined';
        },

        /**
        * True if the detected browser is Opera.
        * @type Boolean
        */
        isOpera: isOpera,
        /**
        * True if the detected browser uses WebKit.
        * @type Boolean
        */
        isWebKit: isWebKit,
        /**
        * True if the detected browser is Chrome.
        * @type Boolean
        */
        isChrome: isChrome,
        /**
        * True if the detected browser is Safari.
        * @type Boolean
        */
        isSafari: isSafari,
        /**
        * True if the detected browser is Safari 3.x.
        * @type Boolean
        */
        isSafari3: isSafari3,
        /**
        * True if the detected browser is Safari 4.x.
        * @type Boolean
        */
        isSafari4: isSafari4,
        /**
        * True if the detected browser is Safari 2.x.
        * @type Boolean
        */
        isSafari2: isSafari2,
        /**
        * True if the detected browser is Internet Explorer.
        * @type Boolean
        */
        isIE: isIE,
        /**
        * True if the detected browser is Internet Explorer 6.x.
        * @type Boolean
        */
        isIE6: isIE6,
        /**
        * True if the detected browser is Internet Explorer 7.x.
        * @type Boolean
        */
        isIE7: isIE7,
        /**
        * True if the detected browser is Internet Explorer 8.x.
        * @type Boolean
        */
        isIE8: isIE8,
        /**
        * True if the detected browser uses the Gecko layout engine (e.g. Mozilla, Firefox).
        * @type Boolean
        */
        isGecko: isGecko,
        /**
        * True if the detected browser uses a pre-Gecko 1.9 layout engine (e.g. Firefox 2.x).
        * @type Boolean
        */
        isGecko2: isGecko2,
        /**
        * True if the detected browser uses a Gecko 1.9+ layout engine (e.g. Firefox 3.x).
        * @type Boolean
        */
        isGecko3: isGecko3,
        /**
        * True if the detected browser is Internet Explorer running in non-strict mode.
        * @type Boolean
        */
        isBorderBox: isBorderBox,
        /**
        * True if the detected platform is Linux.
        * @type Boolean
        */
        isLinux: isLinux,
        /**
        * True if the detected platform is Windows.
        * @type Boolean
        */
        isWindows: isWindows,
        /**
        * True if the detected platform is Mac OS.
        * @type Boolean
        */
        isMac: isMac,
        /**
        * True if the detected platform is Adobe Air.
        * @type Boolean
        */
        isAir: isAir,

        zeroPad: function (num) {
            var s = '0' + num;
            return s.substring(s.length - 2)
            //return ('0'+num).substring(-2); // doesn't work on IE :(
        },

        formatNumber: function (num, n) {
            return parseFloat(num).toFixed(n);
        },

        /**
        * 去除HTML标签 可保留换行指令(标签内容被保留)
        * @param strhtml 处理的字符串
        * @param boolh  是否保留换行指令
        */
        removeHtml: function (strhtml, boolh) {
            var temp = strhtml;
            if (boolh)
                temp = temp.replace(/<P>/ig, '').replace(/<\/P>/ig, "\n").replace(/<br\/?>/gi, "\n");

            temp = temp.replace(/&nbsp;/gi, "").replace(/<[^>]*>/gi, "");
            return temp;
        },

        //获得窗体高度
        getDocumentHeight: function (win) {
            win = win || self;
            if (isIE) {
                return DOC.compatMode == "CSS1Compat" ? win.document.documentElement.clientHeight : win.document.body.clientHeight;
            } else {
                return win.innerHeight;
            }
        },

        //获得窗体宽度
        getDocumentWidth: function (win) {
            win = win || self;
            if (isIE) {
                return DOC.compatMode == "CSS1Compat" ? win.document.documentElement.clientWidth : win.document.body.clientWidth;
            } else {
                return win.innerWidth;
            }
        },

        //获得滚动条位置
        getScrollPosition: function (div) {
            if (!div) {
                return { X: Math.max(DOC.documentElement.scrollLeft, DOC.body.scrollLeft), Y: Math.max(DOC.documentElement.scrollTop, DOC.body.scrollTop) };
            } else {
                return { X: div.scrollLeft, Y: div.scrollTop };
            }
        },

        //刷新当前页面
        pageReload: function () {
            DOC.location.reload();
        },

        //页码跳转
        pageGo: function (url) {
            DOC.location.href = url;
        },

        /**
        * 根据javascript对象的属性,获得值
        */
        getObjectKeyValue: function (obj, key) {
            var value = null;

            $.each(obj, function (k, v) {
                if (k == key) {
                    value = v;
                    return false;
                }
            });
            return value;
        },

        /**
        * 用来获取页面url QueryString的参数
        */
        request: function (paras) {
            var url = location.href;
            var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
            var paraObj = {}
            for (i = 0; j = paraString[i]; i++) {
                paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
            }
            var returnValue = paraObj[paras.toLowerCase()];
            if (typeof (returnValue) == "undefined") {
                return "";
            } else {
                return returnValue;
            }
        },

        /**
        * 设置对象的高度为其父容器的高度
        */
        setDivMaxHeight: function (obj) {
            obj.height = obj.parentNode.offsetHeight;
        },

        /**
        * 当前页面返回上一页
        */
        goBack: function () {
            if (frames.frameElement) {
                frames.frameElement.contentWindow.history.back();
            } else {
                self.history.back();
            }
        },

        getDesktop: function () {
            var desktop = parent;

            while (typeof (desktop.name) != 'undefined' && desktop.name != '') {
                desktop = desktop.parent;
            }

            return desktop;
        },

        /*加法运算*/
        addition: function (arg1, arg2) {
            var r1, r2, m;
            try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
            try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
            m = Math.pow(10, Math.max(r1, r2));
            return (dcmMul(arg1, m) + dcmMul(arg2, m)) / m;
        },

        /*减法运算*/
        substruction: function (arg1, arg2) {
            return this.addition(arg1, -arg2);
        },

        /*乘法运算*/
        multiplication: function (arg1, arg2) {
            var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
            try { m += s1.split(".")[1].length } catch (e) { }
            try { m += s2.split(".")[1].length } catch (e) { }
            return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
        },

        /*除法运算*/
        division: function (arg1, arg2) {
            return this.multiplication(arg1, 1 / arg2);
        }
    });

    /**
    * Creates namespaces to be used for scoping variables and classes so that they are not global.
    * Specifying the last node of a namespace implicitly creates all other nodes. Usage:
    * <pre><code>
    KSN.namespace('Company', 'Company.data');
    KSN.namespace('Company.data'); // equivalent and preferable to above syntax
    Company.Widget = function() { ... }
    Company.data.CustomStore = function(config) { ... }
    </code></pre>
    * @param {String} namespace1
    * @param {String} namespace2
    * @param {String} etc
    * @return {Object} The namespace object. (If multiple arguments are passed, this will be the last namespace created)
    * @method ns
    */
    KSN.ns = KSN.namespace;
})();

/**
* @class Function
* These functions are available on every Function object (any JavaScript function).
*/
KSN.apply(Function.prototype, {
    /**
    * Creates an interceptor function. The passed function is called before the original one. If it returns false,
    * the original one is not called. The resulting function returns the results of the original function.
    * The passed function is called with the parameters of the original function. Example usage:
    * <pre><code>
    var sayHi = function(name){
    alert('Hi, ' + name);
    }

    sayHi('Fred'); // alerts "Hi, Fred"

    // create a new function that validates input without
    // directly modifying the original function:
    var sayHiToFriend = sayHi.createInterceptor(function(name){
    return name == 'Brian';
    });

    sayHiToFriend('Fred');  // no alert
    sayHiToFriend('Brian'); // alerts "Hi, Brian"
    </code></pre>
    * @param {Function} fcn The function to call before the original
    * @param {Object} scope (optional) The scope (<code><b>this</b></code> reference) in which the passed function is executed.
    * <b>If omitted, defaults to the scope in which the original function is called or the browser window.</b>
    * @return {Function} The new function
    */
    createInterceptor: function(fcn, scope) {
        var method = this;
        return !KSN.isFunction(fcn) ?
                this :
                function() {
                    var me = this,
                        args = arguments;
                    fcn.target = me;
                    fcn.method = method;
                    return (fcn.apply(scope || me || window, args) !== false) ?
                            method.apply(me || window, args) :
                            null;
                };
    },

    /**
    * Creates a callback that passes arguments[0], arguments[1], arguments[2], ...
    * Call directly on any function. Example: <code>myFunction.createCallback(arg1, arg2)</code>
    * Will create a function that is bound to those 2 args. <b>If a specific scope is required in the
    * callback, use {@link #createDelegate} instead.</b> The function returned by createCallback always
    * executes in the window scope.
    * <p>This method is required when you want to pass arguments to a callback function.  If no arguments
    * are needed, you can simply pass a reference to the function as a callback (e.g., callback: myFn).
    * However, if you tried to pass a function with arguments (e.g., callback: myFn(arg1, arg2)) the function
    * would simply execute immediately when the code is parsed. Example usage:
    * <pre><code>
    var sayHi = function(name){
    alert('Hi, ' + name);
    }

    // clicking the button alerts "Hi, Fred"
    new KSN.Button({
    text: 'Say Hi',
    renderTo: KSN.getBody(),
    handler: sayHi.createCallback('Fred')
    });
    </code></pre>
    * @return {Function} The new function
    */
    createCallback: function(/*args...*/) {
        // make args available, in function below
        var args = arguments,
            method = this;
        return function() {
            return method.apply(window, args);
        };
    },

    /**
    * Creates a delegate (callback) that sets the scope to obj.
    * Call directly on any function. Example: <code>this.myFunction.createDelegate(this, [arg1, arg2])</code>
    * Will create a function that is automatically scoped to obj so that the <tt>this</tt> variable inside the
    * callback points to obj. Example usage:
    * <pre><code>
    var sayHi = function(name){
    // Note this use of "this.text" here.  This function expects to
    // execute within a scope that contains a text property.  In this
    // example, the "this" variable is pointing to the btn object that
    // was passed in createDelegate below.
    alert('Hi, ' + name + '. You clicked the "' + this.text + '" button.');
    }

    var btn = new KSN.Button({
        text: 'Say Hi',
        renderTo: KSN.getBody()
    });

    // This callback will execute in the scope of the
    // button instance. Clicking the button alerts
    // "Hi, Fred. You clicked the "Say Hi" button."
    btn.on('click', sayHi.createDelegate(btn, ['Fred']));
    </code></pre>
    * @param {Object} scope (optional) The scope (<code><b>this</b></code> reference) in which the function is executed.
    * <b>If omitted, defaults to the browser window.</b>
    * @param {Array} args (optional) Overrides arguments for the call. (Defaults to the arguments passed by the caller)
    * @param {Boolean/Number} appendArgs (optional) if True args are appended to call args instead of overriding,
    * if a number the args are inserted at the specified position
    * @return {Function} The new function
    */
    createDelegate: function(obj, args, appendArgs) {
        var method = this;
        return function() {
            var callArgs = args || arguments;
            if (appendArgs === true) {
                callArgs = Array.prototype.slice.call(arguments, 0);
                callArgs = callArgs.concat(args);
            } else if (KSN.isNumber(appendArgs)) {
                callArgs = Array.prototype.slice.call(arguments, 0); // copy arguments first
                var applyArgs = [appendArgs, 0].concat(args); // create method call params
                Array.prototype.splice.apply(callArgs, applyArgs); // splice them in
            }
            return method.apply(obj || window, callArgs);
        };
    },

    /**
    * Calls this function after the number of millseconds specified, optionally in a specific scope. Example usage:
    * <pre><code>
    var sayHi = function(name){
    alert('Hi, ' + name);
    }

    // executes immediately:
    sayHi('Fred');

    // executes after 2 seconds:
    sayHi.defer(2000, this, ['Fred']);

    // this syntax is sometimes useful for deferring
    // execution of an anonymous function:
    (function(){
    alert('Anonymous');
    }).defer(100);
    </code></pre>
    * @param {Number} millis The number of milliseconds for the setTimeout call (if less than or equal to 0 the function is executed immediately)
    * @param {Object} scope (optional) The scope (<code><b>this</b></code> reference) in which the function is executed.
    * <b>If omitted, defaults to the browser window.</b>
    * @param {Array} args (optional) Overrides arguments for the call. (Defaults to the arguments passed by the caller)
    * @param {Boolean/Number} appendArgs (optional) if True args are appended to call args instead of overriding,
    * if a number the args are inserted at the specified position
    * @return {Number} The timeout id that can be used with clearTimeout
    */
    defer: function(millis, obj, args, appendArgs) {
        var fn = this.createDelegate(obj, args, appendArgs);
        if (millis > 0) {
            return setTimeout(fn, millis);
        }
        fn();
        return 0;
    }
});

/**
* @class String
* These functions are available on every String object.
*/
KSN.applyIf(String.prototype, {
    trim: function () {
        return this.replace(/(^\s*)|(\s*$)/g, "");
    },
    format: function() {
        var args = arguments;
        return this.replace(/\{(\d+)\}/g, function(m, i) {
            return args[i];
        });
    },
    isDate: function () {
        var r = this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
        if (r == null) return false;
        var d = new Date(r[1], r[3] - 1, r[4]);
        return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
    },
    isDateTime: function () {
        var r = this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
        if (r == null) return false;
        var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
        return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6] && d.getSeconds() == r[7]);
    },
    isMail: function () {
        var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        return emailReg.test(this);
    },
    /**
    * Tests if this string starts with a prefix.
    *
    * An optional offset specifies where to start searching,
    * default is 0 (start of the string).
    *
    * Returns false if the offset is negative or greater than the length
    * of this string.
    *
    * @example "goldvein".startsWith("go")
    * @result true
    * 
    * @example "goldvein".startsWith("god")
    * @result false
    *
    * @example "goldvein".startsWith("ld", 2)
    * @result true
    * 
    * @example "goldvein".startsWith("old", 2)
    * @result false
    *
    * @name startsWith
    * @type Boolean
    * @param prefix The prefix to test
    * @param offset (optional) From where to start testing
    * @cat Plugins/Methods/String
    */
    startsWith: function (prefix, offset) {
        var offset = offset || 0;
        if (offset < 0 || offset > this.length) return false;
        return this.substring(offset, offset + prefix.length) == prefix;
    },
    /**
    * Tests if this string ends with the specified suffix.
    *
    * @example "goldvein".endsWith("ein")
    * @result true
    *
    * @example "goldvein".endsWith("vei")
    * @result false
    *
    * @name endsWith
    * @type Boolean
    * @param suffix The suffix to test
    * @cat Plugins/Methods/String
    */
    endsWith: function (suffix) {
        return this.substring(this.length - suffix.length) == suffix;
    },
    /**
    * Returns a new String that is no longer than a certain length.
    *
    * @example "thisistenc ".truncate(5);
    * @result "th..."
    *
    * @example "thisistenc ".truncate(5, "x")
    * @result "thisx"
    *
    * @name truncate
    * @type String
    * @param Number length (optional) The maximum length of the returned string, default is 30
    * @param String suffix (optional) The suffix to append to the truncated string, default is "..."
    * @cat Plugins/Methods/String
    */
    truncate: function (length, suffix) {
        length = length || 30;
        suffix = suffix === undefined ? "..." : suffix;
        return this.length > length ?
			this.slice(0, length - suffix.length) + suffix : this;
    },
    /**
    * Returns a new String with all tags stripped.
    *
    * @example "<div id='hi'>Bla</div>".stripTags()
    * @result "Bla"
    *
    * @name stripTags
    * @type String
    * @cat Plugins/Methods/String
    */
    stripTags: function () {
        return this.replace(/<\/?[^>]+>/gi, '');
    }
});

/**
* @class String
* These functions are available on every String object.
*/
KSN.applyIf(String, {
    format: function(format) {
        var args = KSN.toArray(arguments, 1);
        return format.replace(/\{(\d+)\}/g, function(m, i) {
            return args[i];
        });
    }
});


/**
* @class Array
* These functions are available on every Array object.
*/
KSN.applyIf(Array.prototype, {
    /*
    * Copy  * 深度拷贝
    * return Array
    */
    copy: function () {
        return [].concat(this);
    },
    /*
    * unionSet  * 合并数组,重复者将被移除
    * return Array
    */
    unionSet: function (value) {
        var index = this.indexOf(value);
        if (index > -1) {
            return this;
        } else {
            this.push(value);
            return this;
        }
    },
    /*
    * unique  * 去除重复
    * return Array
    */
    unique: function () {
        var a = {};
        for (var i = 0; i < this.length; i++) {
            if (typeof a[this[i]] == "undefined")
                a[this[i]] = 1;
        }
        this.length = 0;
        for (var i in a)
            this[this.length] = i;
        return this;
    },
    /*
    * Diff
    * 获取一个数组与其他数组中元素所不相同的元素
    * 示例 .myArary.diff([otherArray,otherArray,otherArray...])
    * return Array
    */
    diff: function () {
        var a1 = this;
        var a = a2 = null;
        var n = 0;
        while (n < arguments.length) {
            a = [];
            a2 = arguments[n];
            var l = a1.length;
            var l2 = a2.length;
            var diff = true;
            for (var i = 0; i < l; i++) {
                for (var j = 0; j < l2; j++) {
                    if (a1[i] === a2[j]) {
                        diff = false;
                        break;
                    }
                }
                diff ? a.push(a1[i]) : diff = true;
            }
            a1 = a;
            n++;
        }
        return a.unique();
    },
    /*
    * union  * 合并
    * 示例 myArary.union([otherArray,otherArray,otherArray...])
    * return Array
    */
    union: function (temp) {
        var a = this.concat(temp);
        return a;
    },
    /*
    * intersect * 交集
    * 示例 myArary.intersect([otherArray,otherArray,otherArray...])
    * return Array
    */
    intersect: function () {
        if (!arguments.length)
            return [];
        var a1 = this;
        var a = a2 = null;
        var n = 0;
        while (n < arguments.length) {
            a = [];
            a2 = arguments[n];
            var l = a1.length;
            var l2 = a2.length;
            for (var i = 0; i < l; i++) {
                for (var j = 0; j < l2; j++) {
                    if (a1[i] === a2[j])
                        a.push(a1[i]);
                }
            }
            a1 = a;
            n++;
        }
        return a.unique();
    },
    /*
    * remove 移除指定索引处的元素
    */
    remove: function (i, deleteCount) {
        if (deleteCount === undefined) { deleteCount = 1; }
        this.splice(i, deleteCount);
    },
    /*
    * insert 将元素插入指定索引处
    */
    insert: function (i, data) {
        this.splice(i, 0, data);
    },
    // 实现类似C#中的Contains()
    contains: function (v) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == v) return true;
        }
        return false;
    },
    // 实现类似C#中的IndexOf()
    indexOf: function (o, from) {
        var len = this.length;
        from = from || 0;
        from += (from < 0) ? len : 0;
        for (; from < len; ++from) {
            if (this[from] === o) {
                return from;
            }
        }
        return -1;
    },
    lastIndexOf: function (el, start) {
        var start = start || 0;
        for (var i = this.length - 1; i > 0; i--) {
            if (this[i] === el) {
                return i;
            }
        }
        return -1;
    },
    /* 
    遍历 
    @param function(value,index,array) 返回 "break"
    */
    each: function (f) {
        try {
            for (var i = 0; i < this.length; i++) {
                try {
                    f(this[i], i, this)
                } catch (e) {
                    throw e
                }
            }
        } catch (e) {
        }
    },
    /*
    遍历
    @param function(value,index,arr)

    var arr=[1,2,3,4,5,7,9]
    function foo(v){    //检测是不是偶数
    return v%2==0
    }
    alert(arr.find(foo))
    */
    find: function (f) {
        var result;
        this.each(function (value, index, arr) {
            if (f(value, index, arr)) {
                result = value
                throw $break
            }
        })
        return result
    },
    /*
    every 
    全部通过返回true.
    如果数组中的每个元素都能通过给定的函数的测试，则返回true，反之false。换言之给定的函数也一定要返回true与false 
    如果其他浏览器没有实现此方法，可以用以下代码实现兼容： 
    function isBigEnough(element, index, array) { 
    return (element <= 10); 
    } 
    var passed = [12, 5, 8, 130, 44].every(isBigEnough); 
    // passed is false 
    passed = [12, 54, 18, 130, 44].every(isBigEnough); 
    // passed is true 
    */
    every: function (fn, thisObj) {
        var scope = thisObj || window;
        for (var i = 0, j = this.length; i < j; ++i) {
            if (!fn.call(scope, this[i], i, this)) {
                return false;
            }
        }
        return true;
    },
    /*
    some 
    一个通过就通过
    类似every函数，但只要有一个通过给定函数的测试就返回true。 
    如果其他浏览器没有实现此方法，可以用以下代码实现兼容：

    function isBigEnough(element, index, array) { 
    return (element >= 10); 
    } 
    var passed = [2, 5, 8, 1, 4].some(isBigEnough); 
    // passed is false 
    passed = [12, 5, 8, 1, 4].some(isBigEnough); 
    // passed is true 
    */
    some: function (fn, thisObj) {
        var scope = thisObj || window;
        for (var i = 0, j = this.length; i < j; ++i) {
            if (fn.call(scope, this[i], i, this)) {
                return true;
            }
        }
        return false;
    },
    /*
    filter 
    把符合条件的元素放到一个新数组中返回。 
    如果其他浏览器没有实现此方法，可以用以下代码实现兼容
    function isBigEnough(element, index, array) { 
    return (element <= 10); 
    } 
    var filtered = [12, 5, 8, 130, 44].filter(isBigEnough); 
    */
    filter: function (fn, thisObj) {
        var scope = thisObj || window;
        var a = [];
        for (var i = 0, j = this.length; i < j; ++i) {
            if (!fn.call(scope, this[i], i, this)) {
                continue;
            }
            a.push(this[i]);
        }
        return a;
    },
    /*
    map 
    让数组中的每一个元素调用给定的函数，然后把得到的结果放到新数组中返回。。 

    <div id="highlighter_240589"> 
    var numbers = [1, 4, 9]; 
    var roots = numbers.map(Math.sqrt); 
    // roots is now [1, 2, 3] 
    // numbers is still [1, 4, 9] 
    */
    map: function (fn, thisObj) {
        var scope = thisObj || window;
        var a = [];
        for (var i = 0, j = this.length; i < j; ++i) {
            a.push(fn.call(scope, this[i], i, this));
        }
        return a;
    },
    /*
    reduce 
    让数组元素依次调用给定函数，最后返回一个值，换言之给定函数一定要用返回值。 
    var total = [0, 1, 2, 3].reduce(function(a, b){ return a + b; }); 
    // total == 6  
    */
    reduce: function (fun /*, initial*/) {
        var len = this.length >>> 0;
        if (typeof fun != "function")
            throw new TypeError();
        if (len == 0 && arguments.length == 1)
            throw new TypeError();
        var i = 0;
        if (arguments.length >= 2) {
            var rv = arguments[1];
        } else {
            do {
                if (i in this) {
                    rv = this[i++];
                    break;
                }
                if (++i >= len)
                    throw new TypeError();
            } while (true);
        }

        for (; i < len; i++) {
            if (i in this)
                rv = fun.call(null, rv, this[i], i, this);
        }
        return rv;
    },
    /* 
    * 模仿.net的datatable中的select()方法
    * var a =[{name:'aa',sex:'12'},
    *		{name:'bb',sex:'12'}
    *	    ]
    * a.select("name",'bb');
    * 结果,
    * [
    *	{name:'bb',sex:'12'}
    * ]
    * 没找到返回[]
    */
    select: function (field, value, thisObj) {
        return this.filter(function (item, index, array) {
            if (item[field] == value) {
                return item;
            } else { return false; }
        }, thisObj)
    }
});

/**
* @class Date
* These functions are available on every Date object.
*/
KSN.applyIf(Date, {
    /**
    * An Array of day names starting with Sunday.
    * 
    * @example dayNames[0]
    * @result 'Sunday'
    *
    * @name dayNames
    * @type Array
    * @cat Plugins/Methods/Date
    */
    dayNames: ['日', '一', '二', '三', '四', '五', '六'],
    /**
    * An Array of abbreviated day names starting with Sun.
    * 
    * @example abbrDayNames[0]
    * @result 'Sun'
    *
    * @name abbrDayNames
    * @type Array
    * @cat Plugins/Methods/Date
    */
    abbrDayNames: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    /**
    * An Array of month names starting with Janurary.
    * 
    * @example monthNames[0]
    * @result 'January'
    *
    * @name monthNames
    * @type Array
    * @cat Plugins/Methods/Date
    */
    monthNames: ['一月', '二月', '三月', '四月', '五月', '五月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
    /**
    * An Array of abbreviated month names starting with Jan.
    * 
    * @example abbrMonthNames[0]
    * @result 'Jan'
    *
    * @name monthNames
    * @type Array
    * @cat Plugins/Methods/Date
    */
    abbrMonthNames: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    /**
    * The first day of the week for this locale.
    *
    * @name firstDayOfWeek
    * @type Number
    * @cat Plugins/Methods/Date
    * @author Kelvin Luck
    */
    firstDayOfWeek: 1,
    /**
    * The format that string dates should be represented as (e.g. 'dd/mm/yyyy' for UK, 'mm/dd/yyyy' for US, 'yyyy-mm-dd' for Unicode etc).
    *
    * @name format
    * @type String
    * @cat Plugins/Methods/Date
    * @author Kelvin Luck
    */
    //format: 'dd/mm/yyyy';
    //format: 'mm/dd/yyyy';
    format: 'yyyy-mm-dd',
    //format: 'dd mmm yy';
    
    /**
    * Returns a new date object created from the passed String according to Date.format or false if the attempt to do this results in an invalid date object
    * (We can't simple use Date.parse as it's not aware of locale and I chose not to overwrite it incase it's functionality is being relied on elsewhere)
    *
    * @example var dtm = Date.fromString("12/01/2008");
    * dtm.toString();
    * @result 'Sat Jan 12 2008 00:00:00' // (where Date.format == 'dd/mm/yyyy'
    * 
    * @name fromString
    * @type Date
    * @cat Plugins/Methods/Date
    * @author Kelvin Luck
    */
    fromString: function(s) {
        var f = Date.format;

        var d = new Date('01/01/1970');

        if (s == '') return d;

        s = s.toLowerCase();
        var matcher = '';
        var order = [];
        var r = /(dd?d?|mm?m?|yy?yy?)+([^(m|d|y)])?/g;
        var results;
        while ((results = r.exec(f)) != null) {
            switch (results[1]) {
                case 'd':
                case 'dd':
                case 'm':
                case 'mm':
                case 'yy':
                case 'yyyy':
                    matcher += '(\\d+\\d?\\d?\\d?)+';
                    order.push(results[1].substr(0, 1));
                    break;
                case 'mmm':
                    matcher += '([a-z]{3})';
                    order.push('M');
                    break;
            }
            if (results[2]) {
                matcher += results[2];
            }

        }
        var dm = new RegExp(matcher);
        var result = s.match(dm);
        for (var i = 0; i < order.length; i++) {
            var res = result[i + 1];
            switch (order[i]) {
                case 'd':
                    d.setDate(res);
                    break;
                case 'm':
                    d.setMonth(Number(res) - 1);
                    break;
                case 'M':
                    for (var j = 0; j < Date.abbrMonthNames.length; j++) {
                        if (Date.abbrMonthNames[j].toLowerCase() == res) break;
                    }
                    d.setMonth(j);
                    break;
                case 'y':
                    d.setYear(res);
                    break;
            }
        }

        return d;
    }
});

KSN.applyIf(Date.prototype, {
    /**
    * Checks if the year is a leap year.
    *
    * @example var dtm = new Date("01/12/2008");
    * dtm.isLeapYear();
    * @result true
    *
    * @name isLeapYear
    * @type Boolean
    * @cat Plugins/Methods/Date
    */
    isLeapYear: function() {
        var y = this.getFullYear();
        return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0;
    },

    /**
    * Checks if the day is a weekend day (Sat or Sun).
    *
    * @example var dtm = new Date("01/12/2008");
    * dtm.isWeekend();
    * @result false
    *
    * @name isWeekend
    * @type Boolean
    * @cat Plugins/Methods/Date
    */
    isWeekend: function() {
        return this.getDay() == 0 || this.getDay() == 6;
    },

    /**
    * Check if the day is a day of the week (Mon-Fri)
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.isWeekDay();
    * @result false
    * 
    * @name isWeekDay
    * @type Boolean
    * @cat Plugins/Methods/Date
    */
    isWeekDay: function() {
        return !this.isWeekend();
    },

    /**
    * Gets the number of days in the month.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.getDaysInMonth();
    * @result 31
    * 
    * @name getDaysInMonth
    * @type Number
    * @cat Plugins/Methods/Date
    */
    getDaysInMonth: function() {
        return [31, (this.isLeapYear() ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][this.getMonth()];
    },

    /**
    * Gets the name of the day.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.getDayName();
    * @result 'Saturday'
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.getDayName(true);
    * @result 'Sat'
    * 
    * @param abbreviated Boolean When set to true the name will be abbreviated.
    * @name getDayName
    * @type String
    * @cat Plugins/Methods/Date
    */
    getDayName: function(abbreviated) {
        return abbreviated ? Date.abbrDayNames[this.getDay()] : Date.dayNames[this.getDay()];
    },

    /**
    * Gets the name of the month.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.getMonthName();
    * @result 'Janurary'
    *
    * @example var dtm = new Date("01/12/2008");
    * dtm.getMonthName(true);
    * @result 'Jan'
    * 
    * @param abbreviated Boolean When set to true the name will be abbreviated.
    * @name getDayName
    * @type String
    * @cat Plugins/Methods/Date
    */
    getMonthName: function(abbreviated) {
        return abbreviated ? Date.abbrMonthNames[this.getMonth()] : Date.monthNames[this.getMonth()];
    },

    /**
    * Get the number of the day of the year.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.getDayOfYear();
    * @result 11
    * 
    * @name getDayOfYear
    * @type Number
    * @cat Plugins/Methods/Date
    */
    getDayOfYear: function() {
        var tmpdtm = new Date("1/1/" + this.getFullYear());
        return Math.floor((this.getTime() - tmpdtm.getTime()) / 86400000);
    },

    /**
    * Get the number of the week of the year.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.getWeekOfYear();
    * @result 2
    * 
    * @name getWeekOfYear
    * @type Number
    * @cat Plugins/Methods/Date
    */
    getWeekOfYear: function() {
        return Math.ceil(this.getDayOfYear() / 7);
    },

    /**
    * Set the day of the year.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.setDayOfYear(1);
    * dtm.toString();
    * @result 'Tue Jan 01 2008 00:00:00'
    * 
    * @name setDayOfYear
    * @type Date
    * @cat Plugins/Methods/Date
    */
    setDayOfYear: function(day) {
        this.setMonth(0);
        this.setDate(day);
        return this;
    },

    /**
    * Add a number of years to the date object.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.addYears(1);
    * dtm.toString();
    * @result 'Mon Jan 12 2009 00:00:00'
    * 
    * @name addYears
    * @type Date
    * @cat Plugins/Methods/Date
    */
    addYears: function(num) {
        this.setFullYear(this.getFullYear() + num);
        return this;
    },

    /**
    * Add a number of months to the date object.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.addMonths(1);
    * dtm.toString();
    * @result 'Tue Feb 12 2008 00:00:00'
    * 
    * @name addMonths
    * @type Date
    * @cat Plugins/Methods/Date
    */
    addMonths: function(num) {
        var tmpdtm = this.getDate();

        this.setMonth(this.getMonth() + num);

        if (tmpdtm > this.getDate())
            this.addDays(-this.getDate());

        return this;
    },

    /**
    * Add a number of days to the date object.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.addDays(1);
    * dtm.toString();
    * @result 'Sun Jan 13 2008 00:00:00'
    * 
    * @name addDays
    * @type Date
    * @cat Plugins/Methods/Date
    */
    addDays: function(num) {
        //this.setDate(this.getDate() + num);
        this.setTime(this.getTime() + (num * 86400000));
        return this;
    },

    /**
    * Add a number of hours to the date object.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.addHours(24);
    * dtm.toString();
    * @result 'Sun Jan 13 2008 00:00:00'
    * 
    * @name addHours
    * @type Date
    * @cat Plugins/Methods/Date
    */
    addHours: function(num) {
        this.setHours(this.getHours() + num);
        return this;
    },

    /**
    * Add a number of minutes to the date object.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.addMinutes(60);
    * dtm.toString();
    * @result 'Sat Jan 12 2008 01:00:00'
    * 
    * @name addMinutes
    * @type Date
    * @cat Plugins/Methods/Date
    */
    addMinutes: function(num) {
        this.setMinutes(this.getMinutes() + num);
        return this;
    },

    /**
    * Add a number of seconds to the date object.
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.addSeconds(60);
    * dtm.toString();
    * @result 'Sat Jan 12 2008 00:01:00'
    * 
    * @name addSeconds
    * @type Date
    * @cat Plugins/Methods/Date
    */
    addSeconds: function(num) {
        this.setSeconds(this.getSeconds() + num);
        return this;
    },

    /**
    * Sets the time component of this Date to zero for cleaner, easier comparison of dates where time is not relevant.
    * 
    * @example var dtm = new Date();
    * dtm.zeroTime();
    * dtm.toString();
    * @result 'Sat Jan 12 2008 00:01:00'
    * 
    * @name zeroTime
    * @type Date
    * @cat Plugins/Methods/Date
    * @author Kelvin Luck
    */
    zeroTime: function() {
        this.setMilliseconds(0);
        this.setSeconds(0);
        this.setMinutes(0);
        this.setHours(0);
        return this;
    },

    /**
    * Returns a string representation of the date object according to Date.format.
    * (Date.toString may be used in other places so I purposefully didn't overwrite it)
    * 
    * @example var dtm = new Date("01/12/2008");
    * dtm.asString();
    * @result '12/01/2008' // (where Date.format == 'dd/mm/yyyy'
    * 
    * @name asString
    * @type Date
    * @cat Plugins/Methods/Date
    * @author Kelvin Luck
    */
    asString: function(format) {
        var r = format || Date.format;
        if (r.split('mm').length > 1) { // ugly workaround to make sure we don't replace the m's in e.g. noveMber
            r = r.split('mmmm').join(this.getMonthName(false))
				.split('mmm').join(this.getMonthName(true))
				.split('mm').join(KSN.zeroPad(this.getMonth() + 1))
        } else {
            r = r.split('m').join(this.getMonth() + 1);
        }
        r = r.split('yyyy').join(this.getFullYear())
			.split('yy').join((this.getFullYear() + '').substring(2))
			.split('dd').join(KSN.zeroPad(this.getDate()))
			.split('d').join(this.getDate());
        return r;
    }
});

//数字对象扩展
KSN.applyIf(Number.prototype, {
    /**
    * 加法 (6).add(2) = 8
    */
    add: function (arg) {
        return KSN.addition(this, arg);
    },

    /**
    * 减法 (6).sub(2) = 4
    */
    sub: function (arg) {
        return KSN.substruction(this, arg);
    },

    /**
    * 乘法 (6).mul(2) = 12
    */
    mul: function (arg) {
        return KSN.multiplication(this, arg);
    },

    /**
    *   除法 (6).div(2) = 3
    */
    div: function (arg) {
        return KSN.division(this, arg);
    }
});

/*
* 扩展jquery
*/
jQuery.fn.extend({
    /*
    * 自适应高度
    */
    autoHeight: function (minHeight) {
        var _this = $(this);
        var h = _this.parent().innerHeight();
        _this.siblings(':visible').not('.fl').not('script').not('div.k-menu').each(function () { h = h - $(this).outerHeight(); });
        if (minHeight && h < minHeight) h = minHeight;
        _this.height(h - (_this.outerHeight() - _this.innerHeight()));
    },

    /*
    * 复制高度
    */
    reHeightTo: function (expr) {
        if (!(expr instanceof jQuery)) expr = $(expr);

        $(this).height(expr.height());
    }
});

//JS屏蔽IE的BackSpace键
if (typeof window.event != 'undefined') {
    document.onkeydown = function() {
        var type = event.srcElement.type;
        var code = event.keyCode;
        return ((code != 8 && code != 13) ||
                (type == 'text' && code != 13) ||
                (type == 'textarea') ||
                (type == 'submit' && code == 13))
    }
} else { // FireFox/Others
    document.onkeypress = function(e) {
        var type = e.target.localName.toLowerCase();
        var code = e.keyCode;

        return (code != 8 && code != 13) ||
                (type == 'input' && code != 13) ||
                (type == 'textarea') ||
                (type == 'submit' && code == 13);
    }
}

//观察者模式的主题(消息机制)
function Observer(id) {
    this.ID = id; //实例的ID

    this._events = {}; // {"click":[fn1,fn2] }

    this.AddListener = function (eventname, listener)//添加新的监听器
    {
        if (!this._events[eventname]) {
            this._events[eventname] = new Array();
        }
        this._events[eventname].push(listener); //监听器加入数组
    };

    this.RemoveListener = function (eventname, listener, RemovingCallBack, RemovedCallback) //删除特定监听器,传入删除前回调委托和删除后回调委托
    {
        var _Listeners = this._events[eventname];

        for (var i = 0, ln = _Listeners.length; i < ln; i++) {//遍历当前所有监听器
            if (_Listeners[i] == listener) { //如果存在要删除的监听器
                if (RemovingCallBack != undefined && typeof (RemovingCallBack) == "function") {//如果有传入删除前回调委托
                    var removingEventArgs = { Cancel: false }; //定义回调委托参数
                    RemovingCallBack(listener, removingEventArgs); //调用删除前回调委托
                    if (!removingEventArgs.Cancel) {//如果在删除前回调委托中没有取消删除操作
                        _Listeners.splice(i, 1); //从当前数组中删除监听器
                        if (RemovedCallback != undefined && typeof (RemovedCallback) == "function") { //如果有传入删除后回调委托
                            RemovedCallback(listener, i); //调用删除后回调委托
                        }
                    }
                }
                break; //已找到要删除的监听器,后面的不需要再遍历
            }
        }
        _Listeners = null;
    };

    this.ClearListeners = function (evnetname, ClearingCallBack, ClearedCallBack)//清空所有监听器,传入清空前回调委托和清空后回调委托
    {
        var _Listeners = this._events[eventname];

        if (ClearingCallBack != undefined && typeof (ClearingCallBack) == "function") {//如果有传入删除前回调委托
            var args = { ListenersCount: _Listeners.length, Cancel: false }; //定义回调委托参数,ListenersCount:当前要清空多少监听器,Cancel:是否取消操作
            ClearingCallBack(this, args); //调用清空前回调委托
            if (!args.Cancel) {//如果在清空前回调委托中没有取消清空操作
                _Listeners.length = 0; //清空当前数组中所有元素(监听器)
                if (ClearedCallBack != undefined && typeof (ClearedCallBack) == "function") {//如果有传入删除后回调委托
                    ClearedCallBack(); //调用清空后的回调委托,这里没有再传额外参数
                }
            }
        }
        _Listeners = null;
    };

    this.Fire = function (eventname, arg)//激发事件,实现广播通知(Notify)所有监听器
    {
        var args = [];
        if (arg && (arg.length && arg.constructor !== Array)) //如果传过来的第二个参数是一个 arguments对象
        {
            args = arg;
        }
        else {
            for (var i = 1; i < arguments.length; i++) { //把后面的参数往前移1位
                args.push(arguments[i]);
            }
        }

        var _Listeners = this._events[eventname];
        if (typeof (_Listeners) == "function") {
            _Listeners.apply(arguments.caller, args);
            return;
        }

        if (_Listeners) {
            for (var i = 0, ln = _Listeners.length; i < ln; i++) {//遍历当前所有监听器
                var listener = _Listeners[i]; //单独取出当前的监听器
                listener.apply(arguments.caller, args); //调用监听器(函数,委托),args参数原样传递(推给监听器)
            }
        }
        _Listeners = null;
    };
}
