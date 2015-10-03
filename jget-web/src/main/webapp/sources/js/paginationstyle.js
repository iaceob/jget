/**
 * Created by atlantismonk on 15-10-3.
 */
"<div class="pagination">
    <a class="item">首页</a>
    <a class="item">上一页</a>
    <a class="item current" data-page="1">1</a>
    <a class="item next">下一页</a>
    <a class="item">尾页</a>
    <span class="item">1/1</span>
    <input class="item page-jump" type="text" style="padding: 0px; height: 25px; width: 37px;" autocomplete="off" placeholder="跳轉" data-page="1" data-total="1" data-url="http://localhost:8120/job?page=999999999" onkeyup="!function(a,b,c){var d,e,f;if(13==c.keyCode){if(d=b.getAttribute('data-url'),e=b.getAttribute('data-total'),f=a.parseInt(b.value)||1,!f)return!1;0>=f&amp;&amp;(f=1),f>e&amp;&amp;(f=e),a.location.href=d.replace(/9{9}/,f)}}(window,this,event);">
    </div>"