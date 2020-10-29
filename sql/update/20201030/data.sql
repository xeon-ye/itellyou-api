UPDATE `sys_permission` SET `platform` = 2, `type` = 1, `method` = 1, `data` = '/article/{articleId:\\d+}/comment/{id:\\d+}/child', `remark` = '获取文章子评论列表' WHERE `name` = 'api_article_comment_child_list';
UPDATE `sys_permission` SET `platform` = 2, `type` = 1, `method` = 1, `data` = '/software/{softwareId:\\d+}/comment/{id:\\d+}/child', `remark` = '获取软件子评论列表' WHERE `name` = 'api_software_comment_child_list';