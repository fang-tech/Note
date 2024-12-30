" Insert mode: jk to escape
imap jk <Esc>
imap JK <Esc>

" HJKL 实现更大范围的 hjkl
nmap H ^
nmap L $
nmap J 6j
nmap K 6k

" 访问系统剪切板
set clipboard=unnamed

" 移动到下一段落
nmap [ {
nmap ] }

" 复制整行
nmap Y y$

" 打开文本编辑菜单 = 鼠标右键
exmap contextMenu obcommand editor:context-menu
nmap zl :contextMenu<CR>


" 实现括号的surrend功能 
exmap surround_wiki surround [[ ]]
exmap surround_double_quotes surround " "
exmap surround_single_quotes surround ' '
exmap surround_backticks surround ` `
exmap surround_brackets surround ( )
exmap surround_square_brackets surround [ ]
exmap surround_curly_brackets surround { }
exmap surround_italic surround * *
exmap surround_bold surround ** **
exmap surround_delete surround ~~ ~~
exmap surround_mark surround == ==
exmap surround_math surround $ $

" 必须使用 'map'
map [[ :surround_wiki
nunmap s
vunmap s
map s" :surround_double_quotes<CR>
map s' :surround_single_quotes<CR>
map s` :surround_backticks<CR>
map sb :surround_brackets<CR>
map s( :surround_brackets<CR>
map s) :surround_brackets<CR>
map s[ :surround_square_brackets<CR>
map s] :surround_square_brackets<CR>
map s{ :surround_curly_brackets<CR>
map s} :surround_curly_brackets<CR>
map si :surround_italic<CR>
map sb :surround_bold<CR>
map sd :surround_delete<CR>
map sm :surround_mark<CR>
map s$ :surround_math<CR>

" 实现工作区的分割
exmap vsp obcommand workspace:split-vertical
" 实现工作区的纵向分割
exmap ssp obcommand workspace:split-horizontal


" 聚焦
exmap focusLeft obcommand editor:focus-left
exmap focusRight obcommand editor:focus-right
exmap focusBottom obcommand editor:focus-bottom
exmap focusTop obcommand editor:focus-top
nmap <C-w>h :focusLeft<CR>
nmap <C-w>l :focusRight<CR>
nmap <C-w>j :focusBottom<CR>
nmap <C-w>k :focusTop<CR>
