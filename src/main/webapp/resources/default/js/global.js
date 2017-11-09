/**
 * 表格隔行变换颜色
 * 
 * @param {}
 *            id
 * @param {}
 *            j
 */
function showtable(id, j) {
	var tableid = id; // 表格的id
	var overcolor = '#e1f2f0'; // 鼠标经过颜色
	var color1 = '#f9f9f9'; // 第一种颜色
	var color2 = '#eef2f2'; // 第二种颜色
	var tablename = document.getElementById(tableid);
	var tr = tablename.getElementsByTagName("tr");
	for (var i = j; i < tr.length; i++) {
		tr[i].onmouseover = function() {
			this.style.backgroundColor = overcolor;
		}
		tr[i].onmouseout = function() {
			if (this.rowIndex % 2 == 0) {
				this.style.backgroundColor = color1;
			} else {
				this.style.backgroundColor = color2;
			}
		}
		if (i % 2 == 0) {
			tr[i].style.backgroundColor = color1;
		} else {
			tr[i].style.backgroundColor = color2;
		}
	}
}
