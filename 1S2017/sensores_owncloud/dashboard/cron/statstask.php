<?php
namespace OCA\Dashboard\Cron;

use \OCA\Dashboard\App\Dashboard;

class StatsTask {

    public static function run() {
        if (\OCP\Config::getSystemValue('dashboard_no_cron', false)) {
            return;
        }

        $app = new Dashboard();
        $container = $app->getContainer();
        $container->query('StatsTaskService')->run();
    }

}
