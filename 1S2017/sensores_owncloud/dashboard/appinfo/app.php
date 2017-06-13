<?php

namespace OCA\Dashboard;

use \OCA\Dashboard\App\Dashboard;


$app = new Dashboard;
$c = $app->getContainer();

\OCP\App::addNavigationEntry(array(
    'id' => 'dashboard',
    'order' => 10,
    'href' => \OCP\Util::linkToRoute('dashboard.page.index'),
    'icon' => \OCP\Util::imagePath('dashboard', 'dashboard.png'),
    'name' => $c->query('L10N')->t('Dashboard')
));

/**
 * register admin settings section
 */
\OCP\App::registerAdmin('dashboard', 'settings/settings-admin');

/**
 * cron task
 */
\OCP\BackgroundJob::addRegularTask('\OCA\dashboard\Cron\statsTask', 'run');
