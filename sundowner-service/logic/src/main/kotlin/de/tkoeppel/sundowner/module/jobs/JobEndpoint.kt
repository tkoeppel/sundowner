package de.tkoeppel.sundowner.module.jobs

import de.tkoeppel.sundowner.api.JobsApi
import de.tkoeppel.sundowner.to.jobs.Job
import de.tkoeppel.sundowner.to.jobs.JobTO

class JobEndpoint : JobsApi  {

	override fun getJob(id: Long): JobTO {
		TODO("Not yet implemented")
	}

	override fun getJobResult(id: Long): Job {
		TODO("Not yet implemented")
	}
}