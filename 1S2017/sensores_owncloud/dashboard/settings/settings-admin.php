<?php

namespace OCA\Dashboard;

\OCP\User::checkAdminUser();

$tmpl = new \OCP\Template('dashboard', 'settings-admin');

$tmpl->assign('dashboardGroupsEnabled', Lib\Helper::isDashboardGroupsEnabled());

return $tmpl->fetchPage();
